package com.example.foodcheckai.presentation.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodcheckai.util.GeminiApiHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResultViewModel : ViewModel() {

    private val geminiApiHelper = GeminiApiHelper()

    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState: StateFlow<ResultUiState> = _uiState.asStateFlow()

    fun analyzeIngredients(ingredientText: String) {
        _uiState.update { it.copy(
            isLoading = true,
            ingredientText = ingredientText,
            errorMessage = null
        )}

        viewModelScope.launch {
            val result = geminiApiHelper.analyzeIngredients(ingredientText)

            result.onSuccess { analysisResult ->
                _uiState.update { it.copy(
                    isLoading = false,
                    analysisResult = analysisResult
                )}
            }

            result.onFailure { error ->
                _uiState.update { it.copy(
                    isLoading = false,
                    errorMessage = error.message ?: "Failed to analyze ingredients"
                )}
            }
        }
    }

    fun retryAnalysis() {
        val currentText = _uiState.value.ingredientText
        if (currentText.isNotBlank()) {
            analyzeIngredients(currentText)
        }
    }
}
