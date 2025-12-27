package com.example.foodcheckai.presentation.input

import android.net.Uri

data class InputUiState(
    val ingredientText: String = "",
    val selectedImageUri: Uri? = null,
    val isProcessingOcr: Boolean = false,
    val isAnalyzing: Boolean = false,
    val errorMessage: String? = null,
    val validationError: ValidationError? = null,
    val extractedText: String? = null,
    val showImageOptions: Boolean = false,
    val ocrProgress: Float = 0f
)

sealed class ValidationError {
    data object EmptyInput : ValidationError()
    data object TextTooShort : ValidationError()
    data object InvalidFormat : ValidationError()

    fun getMessage(): String = when (this) {
        EmptyInput -> "Please enter ingredients or upload an image"
        TextTooShort -> "Please enter at least 3 characters"
        InvalidFormat -> "Invalid ingredient format. Please check your input"
    }
}

sealed class InputEvent {
    data class TextChanged(val text: String) : InputEvent()
    data class ImageSelected(val uri: Uri) : InputEvent()
    data object RemoveImage : InputEvent()
    data object AnalyzeClicked : InputEvent()
    data object ClearError : InputEvent()
    data object ToggleImageOptions : InputEvent()
    data object ClearText : InputEvent() // Added to support clearing input
}