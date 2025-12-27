package com.example.foodcheckai.presentation.result.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.foodcheckai.presentation.preview.ComponentPreview
import com.example.foodcheckai.presentation.theme.CautionOrange
import com.example.foodcheckai.presentation.theme.FoodCheckAITheme

@Composable
fun DisclaimerCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CautionOrange.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Disclaimer",
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = CautionOrange.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(4.dp),
                tint = CautionOrange
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Medical Disclaimer",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = CautionOrange
                )

                Text(
                    text = "This analysis is for informational purposes only and should not be considered medical advice. " +
                            "The information provided is based on AI analysis and may not be 100% accurate. " +
                            "Always consult with a qualified healthcare professional or registered dietitian before making " +
                            "dietary changes, especially if you have allergies, health conditions, or specific dietary requirements.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black.copy(alpha = 0.7f),
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight
                )

                Text(
                    text = "Product consumption is at your own discretion and risk.",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = CautionOrange.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@ComponentPreview
@Composable
private fun DisclaimerCardPreview() {
    FoodCheckAITheme {
        Column(modifier = Modifier.padding(16.dp)) {
            DisclaimerCard()
        }
    }
}
