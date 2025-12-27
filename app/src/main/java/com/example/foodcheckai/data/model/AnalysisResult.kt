package com.example.foodcheckai.data.model

data class AnalysisResult(
    val productCategory: ProductCategory,
    val containsEgg: Boolean,
    val containsDairy: Boolean,
    val containsMeat: Boolean,
    val healthScore: Int, // 0-100
    val ingredientBreakdown: List<IngredientInfo>,
    val healthInsights: HealthInsights,
    val categoryPercentages: CategoryPercentages
)

enum class ProductCategory {
    VEGETARIAN,
    NON_VEGETARIAN,
    VEGAN,
    UNKNOWN
}

data class IngredientInfo(
    val name: String,
    val category: IngredientCategory,
    val healthImpact: HealthImpact,
    val description: String
)

enum class IngredientCategory {
    HEALTHY,
    MODERATE,
    UNHEALTHY,
    ALLERGEN,
    PRESERVATIVE,
    ADDITIVE,
    NATURAL
}

enum class HealthImpact {
    POSITIVE,
    NEUTRAL,
    NEGATIVE
}

data class HealthInsights(
    val overallAssessment: String,
    val pros: List<String>,
    val cons: List<String>,
    val recommendations: List<String>
)

data class CategoryPercentages(
    val healthy: Float,
    val moderate: Float,
    val unhealthy: Float
)
