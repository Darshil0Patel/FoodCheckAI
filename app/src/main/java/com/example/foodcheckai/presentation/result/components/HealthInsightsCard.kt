package com.example.foodcheckai.presentation.result.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Lightbulb
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
import com.example.foodcheckai.data.model.HealthInsights
import com.example.foodcheckai.presentation.preview.ComponentPreview
import com.example.foodcheckai.presentation.preview.PreviewData
import com.example.foodcheckai.presentation.theme.AlertRed
import com.example.foodcheckai.presentation.theme.FoodCheckAITheme
import com.example.foodcheckai.presentation.theme.HealthyGreen
import com.example.foodcheckai.presentation.theme.InfoBlue

@Composable
fun HealthInsightsCard(
    healthInsights: HealthInsights,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Health Analysis",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Overall Assessment
            Text(
                text = healthInsights.overallAssessment,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.8f)
            )

            // Pros
            if (healthInsights.pros.isNotEmpty()) {
                InsightSection(
                    title = "Pros",
                    items = healthInsights.pros,
                    color = HealthyGreen,
                    icon = Icons.Default.CheckCircle
                )
            }

            // Cons
            if (healthInsights.cons.isNotEmpty()) {
                InsightSection(
                    title = "Cons",
                    items = healthInsights.cons,
                    color = AlertRed,
                    icon = Icons.Default.ErrorOutline
                )
            }

            // Recommendations
            if (healthInsights.recommendations.isNotEmpty()) {
                InsightSection(
                    title = "Recommendations",
                    items = healthInsights.recommendations,
                    color = InfoBlue,
                    icon = Icons.Default.Lightbulb
                )
            }
        }
    }
}

@Composable
private fun InsightSection(
    title: String,
    items: List<String>,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = color.copy(alpha = 0.15f),
                        shape = CircleShape
                    )
                    .padding(4.dp),
                tint = color
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }

        items.forEach { item ->
            Row(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = "•",
                    style = MaterialTheme.typography.bodyMedium,
                    color = color,
                    modifier = Modifier.width(16.dp)
                )
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black.copy(alpha = 0.8f),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@ComponentPreview
@Composable
private fun HealthInsightsCardPreview() {
    FoodCheckAITheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HealthInsightsCard(healthInsights = PreviewData.healthyHealthInsights)

            HealthInsightsCard(healthInsights = PreviewData.unhealthyHealthInsights)
        }
    }
}
