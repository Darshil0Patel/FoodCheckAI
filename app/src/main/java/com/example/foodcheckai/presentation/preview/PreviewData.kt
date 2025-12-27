package com.example.foodcheckai.presentation.preview

import com.example.foodcheckai.data.model.*
import com.example.foodcheckai.presentation.home.HomeUiState
import com.example.foodcheckai.presentation.input.InputUiState
import com.example.foodcheckai.presentation.input.ValidationError
import com.example.foodcheckai.presentation.result.ResultUiState

/**
 * Centralized preview mock data for Jetpack Compose previews
 *
 * Provides realistic sample data for all UI components, covering:
 * - Different product categories (vegan, vegetarian, non-veg, unknown)
 * - Various health scores and ingredient combinations
 * - All UI states (loading, success, error, empty)
 * - Edge cases (long text, empty lists, high/low values)
 */
object PreviewData {

    // ===== Ingredient Mock Data =====

    val sampleIngredients = listOf(
        IngredientInfo(
            name = "Whole Wheat Flour",
            category = IngredientCategory.HEALTHY,
            healthImpact = HealthImpact.POSITIVE,
            description = "Rich in fiber and nutrients, aids digestion and provides sustained energy"
        ),
        IngredientInfo(
            name = "Sugar",
            category = IngredientCategory.MODERATE,
            healthImpact = HealthImpact.NEUTRAL,
            description = "Provides quick energy but should be consumed in moderation"
        ),
        IngredientInfo(
            name = "High Fructose Corn Syrup",
            category = IngredientCategory.UNHEALTHY,
            healthImpact = HealthImpact.NEGATIVE,
            description = "Linked to obesity, diabetes, and metabolic issues when consumed frequently"
        ),
        IngredientInfo(
            name = "Soy Lecithin",
            category = IngredientCategory.ALLERGEN,
            healthImpact = HealthImpact.NEUTRAL,
            description = "Common allergen, emulsifier derived from soybeans"
        ),
        IngredientInfo(
            name = "Sodium Benzoate",
            category = IngredientCategory.PRESERVATIVE,
            healthImpact = HealthImpact.NEGATIVE,
            description = "Preservative that may form benzene when combined with vitamin C"
        ),
        IngredientInfo(
            name = "Turmeric Extract",
            category = IngredientCategory.NATURAL,
            healthImpact = HealthImpact.POSITIVE,
            description = "Natural anti-inflammatory compound with antioxidant properties"
        )
    )

    val healthyIngredients = listOf(
        IngredientInfo(
            name = "Organic Oats",
            category = IngredientCategory.HEALTHY,
            healthImpact = HealthImpact.POSITIVE,
            description = "Excellent source of fiber, helps lower cholesterol"
        ),
        IngredientInfo(
            name = "Almonds",
            category = IngredientCategory.NATURAL,
            healthImpact = HealthImpact.POSITIVE,
            description = "Rich in healthy fats, protein, and vitamin E"
        ),
        IngredientInfo(
            name = "Honey",
            category = IngredientCategory.NATURAL,
            healthImpact = HealthImpact.POSITIVE,
            description = "Natural sweetener with antioxidants and antimicrobial properties"
        )
    )

    val unhealthyIngredients = listOf(
        IngredientInfo(
            name = "Partially Hydrogenated Oil",
            category = IngredientCategory.UNHEALTHY,
            healthImpact = HealthImpact.NEGATIVE,
            description = "Contains trans fats linked to heart disease and inflammation"
        ),
        IngredientInfo(
            name = "Artificial Color Red 40",
            category = IngredientCategory.ADDITIVE,
            healthImpact = HealthImpact.NEGATIVE,
            description = "Synthetic dye linked to hyperactivity in children"
        ),
        IngredientInfo(
            name = "MSG (Monosodium Glutamate)",
            category = IngredientCategory.ADDITIVE,
            healthImpact = HealthImpact.NEGATIVE,
            description = "Flavor enhancer that may cause headaches and sensitivity reactions"
        )
    )

    // ===== Health Insights Mock Data =====

    val healthyHealthInsights = HealthInsights(
        overallAssessment = "This product contains a good balance of healthy ingredients with minimal unhealthy additives. It's a nutritious choice for regular consumption.",
        pros = listOf(
            "High fiber content from whole grains",
            "Contains natural antioxidants",
            "No artificial colors or flavors",
            "Good source of essential vitamins and minerals",
            "Low in saturated fats"
        ),
        cons = listOf(
            "Contains moderate sugar levels",
            "Includes soy-based allergens"
        ),
        recommendations = listOf(
            "Suitable for daily consumption in moderate portions",
            "Check allergen information if sensitive to soy",
            "Pair with protein for balanced nutrition",
            "Store in a cool, dry place to maintain freshness"
        )
    )

    val moderateHealthInsights = HealthInsights(
        overallAssessment = "This product has a mix of both beneficial and less healthy ingredients. Moderate consumption is recommended.",
        pros = listOf(
            "Contains some whole grain ingredients",
            "Fortified with vitamins",
            "Convenient and shelf-stable"
        ),
        cons = listOf(
            "Higher sugar content than recommended",
            "Contains preservatives",
            "Some artificial additives present",
            "Moderate sodium levels"
        ),
        recommendations = listOf(
            "Limit consumption to occasional treats",
            "Look for lower sugar alternatives",
            "Consume with fresh fruits or vegetables",
            "Check serving sizes to avoid overconsumption"
        )
    )

    val unhealthyHealthInsights = HealthInsights(
        overallAssessment = "This product contains multiple unhealthy ingredients and preservatives that may negatively impact long-term health. Best consumed sparingly.",
        pros = listOf(
            "Long shelf life",
            "Convenient packaging"
        ),
        cons = listOf(
            "High in artificial preservatives and additives",
            "Excessive sugar and sodium content",
            "Contains trans fats from hydrogenated oils",
            "Multiple artificial colors and flavors",
            "Very low nutritional value",
            "May trigger allergic reactions"
        ),
        recommendations = listOf(
            "Consume very sparingly, only as an occasional treat",
            "Look for healthier alternatives with natural ingredients",
            "Consider preparing homemade versions",
            "Read labels carefully and choose products with fewer additives",
            "Prioritize whole, unprocessed foods"
        )
    )

    // ===== Category Percentages Mock Data =====

    val healthyCategoryPercentages = CategoryPercentages(
        healthy = 65f,
        moderate = 25f,
        unhealthy = 10f
    )

    val moderateCategoryPercentages = CategoryPercentages(
        healthy = 35f,
        moderate = 45f,
        unhealthy = 20f
    )

    val unhealthyCategoryPercentages = CategoryPercentages(
        healthy = 15f,
        moderate = 25f,
        unhealthy = 60f
    )

    val veryHealthyCategoryPercentages = CategoryPercentages(
        healthy = 90f,
        moderate = 10f,
        unhealthy = 0f
    )

    // ===== Analysis Result Mock Data =====

    val veganAnalysisResult = AnalysisResult(
        productCategory = ProductCategory.VEGAN,
        containsEgg = false,
        containsDairy = false,
        containsMeat = false,
        healthScore = 85,
        ingredientBreakdown = healthyIngredients,
        healthInsights = healthyHealthInsights,
        categoryPercentages = healthyCategoryPercentages
    )

    val vegetarianAnalysisResult = AnalysisResult(
        productCategory = ProductCategory.VEGETARIAN,
        containsEgg = true,
        containsDairy = true,
        containsMeat = false,
        healthScore = 65,
        ingredientBreakdown = sampleIngredients,
        healthInsights = moderateHealthInsights,
        categoryPercentages = moderateCategoryPercentages
    )

    val nonVegetarianAnalysisResult = AnalysisResult(
        productCategory = ProductCategory.NON_VEGETARIAN,
        containsEgg = true,
        containsDairy = true,
        containsMeat = true,
        healthScore = 45,
        ingredientBreakdown = unhealthyIngredients,
        healthInsights = unhealthyHealthInsights,
        categoryPercentages = unhealthyCategoryPercentages
    )

    val unknownAnalysisResult = AnalysisResult(
        productCategory = ProductCategory.UNKNOWN,
        containsEgg = false,
        containsDairy = false,
        containsMeat = false,
        healthScore = 50,
        ingredientBreakdown = emptyList(),
        healthInsights = HealthInsights(
            overallAssessment = "Unable to determine product category from provided information. Please provide clearer ingredient list.",
            pros = emptyList(),
            cons = listOf("Insufficient ingredient information"),
            recommendations = listOf(
                "Please provide a clearer image of the ingredients list",
                "Ensure text is readable and well-lit",
                "Try typing ingredients manually if OCR fails"
            )
        ),
        categoryPercentages = CategoryPercentages(0f, 0f, 0f)
    )

    val veryHealthyAnalysisResult = AnalysisResult(
        productCategory = ProductCategory.VEGAN,
        containsEgg = false,
        containsDairy = false,
        containsMeat = false,
        healthScore = 95,
        ingredientBreakdown = healthyIngredients,
        healthInsights = healthyHealthInsights,
        categoryPercentages = veryHealthyCategoryPercentages
    )

    // ===== Home UI State Mock Data =====

    val homeUiStateDefault = HomeUiState(
        scanCount = 0,
        isLoading = false,
        showHowItWorksDialog = false
    )

    val homeUiStateWithScans = HomeUiState(
        scanCount = 42,
        isLoading = false,
        showHowItWorksDialog = false
    )

    val homeUiStateWithManyScans = HomeUiState(
        scanCount = 1337,
        isLoading = false,
        showHowItWorksDialog = false
    )

    val homeUiStateLoading = HomeUiState(
        scanCount = 5,
        isLoading = true,
        showHowItWorksDialog = false
    )

    // ===== Input UI State Mock Data =====

    val inputUiStateEmpty = InputUiState(
        ingredientText = "",
        selectedImageUri = null,
        isProcessingOcr = false,
        isAnalyzing = false,
        errorMessage = null,
        validationError = null,
        extractedText = null,
        showImageOptions = false,
        ocrProgress = 0f
    )

    val inputUiStateWithText = InputUiState(
        ingredientText = "Wheat flour, sugar, palm oil, cocoa powder, soy lecithin, vanilla extract, salt",
        selectedImageUri = null,
        isProcessingOcr = false,
        isAnalyzing = false,
        errorMessage = null,
        validationError = null,
        extractedText = null,
        showImageOptions = false,
        ocrProgress = 0f
    )

    val inputUiStateWithLongText = InputUiState(
        ingredientText = """
            Enriched Wheat Flour (Wheat Flour, Niacin, Reduced Iron, Thiamine Mononitrate,
            Riboflavin, Folic Acid), Sugar, Palm Oil, Cocoa Powder (Processed with Alkali),
            High Fructose Corn Syrup, Soy Lecithin, Cornstarch, Salt, Artificial Flavor,
            Sodium Bicarbonate, Monocalcium Phosphate, Sodium Aluminum Phosphate,
            Natural and Artificial Vanilla Flavor, Caramel Color, Red 40, Yellow 5, Blue 1
        """.trimIndent(),
        selectedImageUri = null,
        isProcessingOcr = false,
        isAnalyzing = false,
        errorMessage = null,
        validationError = null,
        extractedText = null,
        showImageOptions = false,
        ocrProgress = 0f
    )

    val inputUiStateAnalyzing = InputUiState(
        ingredientText = "Wheat flour, sugar, palm oil",
        selectedImageUri = null,
        isProcessingOcr = false,
        isAnalyzing = true,
        errorMessage = null,
        validationError = null,
        extractedText = null,
        showImageOptions = false,
        ocrProgress = 0f
    )

    val inputUiStateProcessingOcr = InputUiState(
        ingredientText = "",
        selectedImageUri = null,
        isProcessingOcr = true,
        isAnalyzing = false,
        errorMessage = null,
        validationError = null,
        extractedText = null,
        showImageOptions = false,
        ocrProgress = 0.65f
    )

    val inputUiStateWithError = InputUiState(
        ingredientText = "",
        selectedImageUri = null,
        isProcessingOcr = false,
        isAnalyzing = false,
        errorMessage = "Failed to process image. Please try again.",
        validationError = null,
        extractedText = null,
        showImageOptions = false,
        ocrProgress = 0f
    )

    val inputUiStateWithValidationError = InputUiState(
        ingredientText = "",
        selectedImageUri = null,
        isProcessingOcr = false,
        isAnalyzing = false,
        errorMessage = null,
        validationError = ValidationError.EmptyInput,
        extractedText = null,
        showImageOptions = false,
        ocrProgress = 0f
    )

    val inputUiStateShowingImageOptions = InputUiState(
        ingredientText = "",
        selectedImageUri = null,
        isProcessingOcr = false,
        isAnalyzing = false,
        errorMessage = null,
        validationError = null,
        extractedText = null,
        showImageOptions = true,
        ocrProgress = 0f
    )

    // ===== Result UI State Mock Data =====

    val resultUiStateLoading = ResultUiState(
        isLoading = true,
        analysisResult = null,
        errorMessage = null,
        ingredientText = ""
    )

    val resultUiStateSuccess = ResultUiState(
        isLoading = false,
        analysisResult = vegetarianAnalysisResult,
        errorMessage = null,
        ingredientText = "Wheat flour, milk, eggs, sugar, butter"
    )

    val resultUiStateVegan = ResultUiState(
        isLoading = false,
        analysisResult = veganAnalysisResult,
        errorMessage = null,
        ingredientText = "Organic oats, almonds, honey"
    )

    val resultUiStateNonVeg = ResultUiState(
        isLoading = false,
        analysisResult = nonVegetarianAnalysisResult,
        errorMessage = null,
        ingredientText = "Chicken extract, hydrogenated oil, MSG"
    )

    val resultUiStateVeryHealthy = ResultUiState(
        isLoading = false,
        analysisResult = veryHealthyAnalysisResult,
        errorMessage = null,
        ingredientText = "Organic whole grain oats, almonds, dates"
    )

    val resultUiStateError = ResultUiState(
        isLoading = false,
        analysisResult = null,
        errorMessage = "Failed to analyze ingredients. Please check your internet connection and try again.",
        ingredientText = "Sample ingredients"
    )

    val resultUiStateNetworkError = ResultUiState(
        isLoading = false,
        analysisResult = null,
        errorMessage = "Network error: Unable to connect to the server. Please check your internet connection.",
        ingredientText = ""
    )
}
