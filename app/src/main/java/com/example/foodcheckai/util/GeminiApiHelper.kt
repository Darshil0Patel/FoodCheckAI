package com.example.foodcheckai.util

import com.example.foodcheckai.BuildConfig
import com.example.foodcheckai.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiApiHelper {

    private val apiKey: String
    private val client: OkHttpClient
    private val json = Json { ignoreUnknownKeys = true }

    // Gemini API v1 stable endpoint
    private val baseUrl = "https://generativelanguage.googleapis.com/v1"
    private val modelName = "gemini-2.5-flash" // Latest stable model

    init {
        // Debug: Check if API key is loaded
        apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank()) {
            throw IllegalStateException("Gemini API key is not configured. Please add GEMINI_API_KEY to local.properties")
        }
        println("🔑 Gemini API Key loaded: ${apiKey.take(10)}...")
        println("🌐 Using Gemini API v1 (stable) with model: $modelName")

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    suspend fun analyzeIngredients(ingredientText: String): Result<AnalysisResult> {
        return withContext(Dispatchers.IO) {
            try {
                println("📤 Sending request to Gemini API v1...")
                val prompt = buildAnalysisPrompt(ingredientText)

                // Build request body
                val requestBody = buildRequestBody(prompt)
                val url = "$baseUrl/models/$modelName:generateContent?key=$apiKey"

                val request = Request.Builder()
                    .url(url)
                    .post(requestBody.toRequestBody("application/json".toMediaType()))
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (!response.isSuccessful) {
                    val errorMsg = "API Error ${response.code}: ${responseBody ?: "Unknown error"}"
                    println("❌ $errorMsg")
                    return@withContext Result.failure(Exception(errorMsg))
                }

                if (responseBody == null) {
                    return@withContext Result.failure(Exception("Empty response from API"))
                }

                println("📥 Received response from Gemini API v1")
                println("📝 Response preview: ${responseBody.take(100)}...")

                // Parse response
                val geminiResponse = json.decodeFromString<GeminiResponse>(responseBody)
                val text = geminiResponse.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    ?: throw Exception("No text in response")

                parseAnalysisResponse(text, ingredientText)
            } catch (e: Exception) {
                println("❌ Error analyzing ingredients: ${e.message}")
                e.printStackTrace()

                val errorMessage = when {
                    e.message?.contains("401") == true || e.message?.contains("API_KEY_INVALID") == true ->
                        "Invalid API key. Please check your GEMINI_API_KEY in local.properties"
                    e.message?.contains("403") == true ->
                        "API access forbidden. Make sure your API key has access to Gemini API"
                    e.message?.contains("429") == true ->
                        "API quota exceeded. Please try again later or upgrade your plan"
                    e.message?.contains("timeout") == true || e.message?.contains("Unable to resolve host") == true ->
                        "Network error. Please check your internet connection"
                    else -> "Failed to analyze ingredients: ${e.message}"
                }

                Result.failure(Exception(errorMessage))
            }
        }
    }

    private fun buildRequestBody(prompt: String): String {
        return """
        {
          "contents": [{
            "parts": [{
              "text": ${JSONObject.quote(prompt)}
            }]
          }],
          "generationConfig": {
            "temperature": 0.7,
            "topK": 40,
            "topP": 0.95,
            "maxOutputTokens": 2048
          }
        }
        """.trimIndent()
    }

    private fun buildAnalysisPrompt(ingredients: String): String {
        return """
            Analyze the following food product ingredients and provide a detailed analysis in JSON format.

            Ingredients: $ingredients

            Please provide the analysis in the following JSON structure:
            {
              "productCategory": "VEGETARIAN|NON_VEGETARIAN|VEGAN|UNKNOWN",
              "containsEgg": true/false,
              "containsDairy": true/false,
              "containsMeat": true/false,
              "healthScore": 0-100,
              "categoryPercentages": {
                "healthy": 0-100,
                "moderate": 0-100,
                "unhealthy": 0-100
              },
              "ingredientBreakdown": [
                {
                  "name": "ingredient name",
                  "category": "HEALTHY|MODERATE|UNHEALTHY|ALLERGEN|PRESERVATIVE|ADDITIVE|NATURAL",
                  "healthImpact": "POSITIVE|NEUTRAL|NEGATIVE",
                  "description": "brief description"
                }
              ],
              "healthInsights": {
                "overallAssessment": "brief overall health assessment",
                "pros": ["pro 1", "pro 2"],
                "cons": ["con 1", "con 2"],
                "recommendations": ["recommendation 1", "recommendation 2"]
              }
            }

            Provide only the JSON response, no additional text.
        """.trimIndent()
    }

    private fun parseAnalysisResponse(responseText: String, originalIngredients: String): Result<AnalysisResult> {
        return try {
            // Extract JSON from response (sometimes AI adds markdown code blocks)
            val jsonText = responseText
                .trim()
                .removePrefix("```json")
                .removePrefix("```")
                .removeSuffix("```")
                .trim()

            val json = JSONObject(jsonText)

            val productCategory = when (json.getString("productCategory")) {
                "VEGETARIAN" -> ProductCategory.VEGETARIAN
                "NON_VEGETARIAN" -> ProductCategory.NON_VEGETARIAN
                "VEGAN" -> ProductCategory.VEGAN
                else -> ProductCategory.UNKNOWN
            }

            val categoryPercentages = json.getJSONObject("categoryPercentages")
            val percentages = CategoryPercentages(
                healthy = categoryPercentages.getDouble("healthy").toFloat(),
                moderate = categoryPercentages.getDouble("moderate").toFloat(),
                unhealthy = categoryPercentages.getDouble("unhealthy").toFloat()
            )

            val ingredientBreakdownArray = json.getJSONArray("ingredientBreakdown")
            val ingredientBreakdown = mutableListOf<IngredientInfo>()
            for (i in 0 until ingredientBreakdownArray.length()) {
                val item = ingredientBreakdownArray.getJSONObject(i)
                ingredientBreakdown.add(
                    IngredientInfo(
                        name = item.getString("name"),
                        category = parseIngredientCategory(item.getString("category")),
                        healthImpact = parseHealthImpact(item.getString("healthImpact")),
                        description = item.getString("description")
                    )
                )
            }

            val healthInsightsJson = json.getJSONObject("healthInsights")
            val healthInsights = HealthInsights(
                overallAssessment = healthInsightsJson.getString("overallAssessment"),
                pros = parseJsonArray(healthInsightsJson.getJSONArray("pros")),
                cons = parseJsonArray(healthInsightsJson.getJSONArray("cons")),
                recommendations = parseJsonArray(healthInsightsJson.getJSONArray("recommendations"))
            )

            val result = AnalysisResult(
                productCategory = productCategory,
                containsEgg = json.getBoolean("containsEgg"),
                containsDairy = json.getBoolean("containsDairy"),
                containsMeat = json.getBoolean("containsMeat"),
                healthScore = json.getInt("healthScore"),
                ingredientBreakdown = ingredientBreakdown,
                healthInsights = healthInsights,
                categoryPercentages = percentages
            )

            Result.success(result)
        } catch (e: Exception) {
            Result.failure(Exception("Failed to parse AI response: ${e.message}"))
        }
    }

    private fun parseIngredientCategory(category: String): IngredientCategory {
        return when (category.uppercase()) {
            "HEALTHY" -> IngredientCategory.HEALTHY
            "MODERATE" -> IngredientCategory.MODERATE
            "UNHEALTHY" -> IngredientCategory.UNHEALTHY
            "ALLERGEN" -> IngredientCategory.ALLERGEN
            "PRESERVATIVE" -> IngredientCategory.PRESERVATIVE
            "ADDITIVE" -> IngredientCategory.ADDITIVE
            "NATURAL" -> IngredientCategory.NATURAL
            else -> IngredientCategory.MODERATE
        }
    }

    private fun parseHealthImpact(impact: String): HealthImpact {
        return when (impact.uppercase()) {
            "POSITIVE" -> HealthImpact.POSITIVE
            "NEUTRAL" -> HealthImpact.NEUTRAL
            "NEGATIVE" -> HealthImpact.NEGATIVE
            else -> HealthImpact.NEUTRAL
        }
    }

    private fun parseJsonArray(jsonArray: JSONArray): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray.getString(i))
        }
        return list
    }

    // Data classes for Gemini API v1 response
    @Serializable
    data class GeminiResponse(
        val candidates: List<Candidate>? = null
    )

    @Serializable
    data class Candidate(
        val content: Content? = null
    )

    @Serializable
    data class Content(
        val parts: List<Part>? = null
    )

    @Serializable
    data class Part(
        val text: String? = null
    )
}
