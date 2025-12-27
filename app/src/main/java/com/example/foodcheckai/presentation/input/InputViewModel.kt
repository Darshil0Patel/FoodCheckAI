package com.example.foodcheckai.presentation.input

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodcheckai.util.OcrHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InputViewModel(application: Application) : AndroidViewModel(application) {

    private val ocrHelper = OcrHelper(application)

    private val _uiState = MutableStateFlow(InputUiState())
    val uiState: StateFlow<InputUiState> = _uiState.asStateFlow()

    fun onEvent(event: InputEvent) {
        when (event) {
            is InputEvent.TextChanged -> {
                _uiState.update { it.copy(
                    ingredientText = event.text,
                    validationError = null,
                    errorMessage = null
                )}
            }

            is InputEvent.ImageSelected -> {
                handleImageSelected(event.uri)
            }

            InputEvent.RemoveImage -> {
                _uiState.update { it.copy(
                    selectedImageUri = null,
                    extractedText = null
                )}
            }

            InputEvent.AnalyzeClicked -> {
                validateAndAnalyze()
            }

            InputEvent.ClearError -> {
                _uiState.update { it.copy(
                    errorMessage = null,
                    validationError = null
                )}
            }

            InputEvent.ToggleImageOptions -> {
                _uiState.update { it.copy(
                    showImageOptions = !it.showImageOptions
                )}
            }

            InputEvent.ClearText -> {
                _uiState.update { it.copy(
                    ingredientText = "",
                    validationError = null
                )}
            }
        }
    }

    private fun handleImageSelected(uri: Uri) {
        _uiState.update { it.copy(
            selectedImageUri = uri,
            isProcessingOcr = true,
            errorMessage = null,
            validationError = null,
            showImageOptions = false
        )}

        viewModelScope.launch {
            val result = ocrHelper.extractTextFromImage(uri)

            result.onSuccess { text ->
                _uiState.update { it.copy(
                    extractedText = text,
                    ingredientText = text,
                    isProcessingOcr = false
                )}
            }

            result.onFailure { error ->
                _uiState.update { it.copy(
                    errorMessage = error.message ?: "Failed to extract text from image",
                    isProcessingOcr = false
                )}
            }
        }
    }

    private fun validateAndAnalyze() {
        // 1. Sanitize text to remove symbols that break API requests/JSON parsing
        val rawText = _uiState.value.ingredientText
        val sanitizedText = formatIngredientText(rawText)

        // 2. Update UI so the user sees the cleaned text
        _uiState.update { it.copy(ingredientText = sanitizedText) }

        val currentText = sanitizedText.trim()

        val validationError = when {
            currentText.isEmpty() && _uiState.value.selectedImageUri == null ->
                ValidationError.EmptyInput
            currentText.length < 3 ->
                ValidationError.TextTooShort
            !isValidIngredientText(currentText) ->
                ValidationError.InvalidFormat
            else -> null
        }

        if (validationError != null) {
            _uiState.update { it.copy(validationError = validationError) }
            return
        }

        _uiState.update { it.copy(
            isAnalyzing = true,
            validationError = null,
            errorMessage = null
        )}
    }

    /**
     * Cleans OCR text by keeping only letters, numbers, commas, and periods.
     * This prevents "unterminated object" errors caused by symbols like % or &.
     */
    private fun formatIngredientText(text: String): String {
        return text
            .replace("\n", " ")
            .replace(Regex("[^a-zA-Z0-9\\s,.]"), "") // Strips special symbols
            .replace(Regex("\\s+"), " ") // Fixes double spaces
            .trim()
    }

    private fun isValidIngredientText(text: String): Boolean {
        return text.any { it.isLetter() }
    }

    fun resetAnalyzingState() {
        _uiState.update { it.copy(isAnalyzing = false) }
    }

    override fun onCleared() {
        super.onCleared()
        ocrHelper.close()
    }
}