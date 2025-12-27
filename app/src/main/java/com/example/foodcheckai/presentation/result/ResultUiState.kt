package com.example.foodcheckai.presentation.result

import com.example.foodcheckai.data.model.AnalysisResult

data class ResultUiState(
    val isLoading: Boolean = true,
    val analysisResult: AnalysisResult? = null,
    val errorMessage: String? = null,
    val ingredientText: String = ""
)
