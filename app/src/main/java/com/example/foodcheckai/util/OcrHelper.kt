package com.example.foodcheckai.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await
import java.io.IOException

class OcrHelper(private val context: Context) {

    private val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    suspend fun extractTextFromImage(uri: Uri): Result<String> {
        return try {
            val bitmap = uriToBitmap(uri)
                ?: return Result.failure(Exception("Failed to load image"))

            val inputImage = InputImage.fromBitmap(bitmap, 0)
            val visionText = textRecognizer.process(inputImage).await()

            val extractedText = visionText.text
            if (extractedText.isBlank()) {
                Result.failure(Exception("No text found in image. Please try a clearer image."))
            } else {
                Result.success(extractedText)
            }
        } catch (e: Exception) {
            Result.failure(Exception("OCR failed: ${e.message}"))
        }
    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: IOException) {
            null
        }
    }

    fun close() {
        textRecognizer.close()
    }
}
