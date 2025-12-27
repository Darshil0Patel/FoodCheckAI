package com.example.foodcheckai.presentation.input.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.foodcheckai.presentation.input.ValidationError
import com.example.foodcheckai.presentation.theme.AlertRed
import com.example.foodcheckai.presentation.theme.InfoBlue

@Composable
fun IngredientTextField(
    text: String,
    onTextChange: (String) -> Unit,
    onClearText: () -> Unit, // New parameter for clearing
    validationError: ValidationError?,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            label = {
                Text("Ingredient List")
            },
            placeholder = {
                Text("e.g., Water, Sugar, Flour, Eggs, Milk...")
            },
            trailingIcon = {
                // Shows the "X" button only if there is text and the field is enabled
                if (text.isNotEmpty() && enabled) {
                    IconButton(onClick = onClearText) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear text",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            supportingText = {
                Text(
                    text = "Enter ingredients separated by commas",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            isError = validationError != null,
            minLines = 6,
            maxLines = 10,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = InfoBlue,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                errorBorderColor = AlertRed,
                focusedLabelColor = InfoBlue,
                cursorColor = InfoBlue
            )
        )

        // Error message
        AnimatedVisibility(
            visible = validationError != null,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            validationError?.let { error ->
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = AlertRed.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Error",
                        tint = AlertRed,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = error.getMessage(),
                        style = MaterialTheme.typography.bodySmall,
                        color = AlertRed,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}