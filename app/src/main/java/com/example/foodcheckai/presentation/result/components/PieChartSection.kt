package com.example.foodcheckai.presentation.result.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.foodcheckai.data.model.CategoryPercentages
import com.example.foodcheckai.presentation.preview.ComponentPreview
import com.example.foodcheckai.presentation.preview.PreviewData
import com.example.foodcheckai.presentation.theme.AlertRed
import com.example.foodcheckai.presentation.theme.CautionOrange
import com.example.foodcheckai.presentation.theme.FoodCheckAITheme
import com.example.foodcheckai.presentation.theme.HealthyGreen

@Composable
fun PieChartSection(
    categoryPercentages: CategoryPercentages,
    healthScore: Int,
    modifier: Modifier = Modifier
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1500)
        )
    }

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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ingredient Breakdown",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Pie Chart
            Box(
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier.size(200.dp)
                ) {
                    val canvasSize = size.minDimension
                    val radius = canvasSize / 2
                    val strokeWidth = 40.dp.toPx()

                    val total = categoryPercentages.healthy + categoryPercentages.moderate + categoryPercentages.unhealthy
                    if (total == 0f) return@Canvas

                    var startAngle = -90f

                    // Draw healthy segment
                    val healthyAngle = (categoryPercentages.healthy / total) * 360f * animatedProgress.value
                    if (healthyAngle > 0) {
                        drawArc(
                            color = HealthyGreen,
                            startAngle = startAngle,
                            sweepAngle = healthyAngle,
                            useCenter = false,
                            style = Stroke(width = strokeWidth),
                            topLeft = Offset((canvasSize - radius * 2) / 2, (canvasSize - radius * 2) / 2),
                            size = Size(radius * 2, radius * 2)
                        )
                        startAngle += healthyAngle
                    }

                    // Draw moderate segment
                    val moderateAngle = (categoryPercentages.moderate / total) * 360f * animatedProgress.value
                    if (moderateAngle > 0) {
                        drawArc(
                            color = CautionOrange,
                            startAngle = startAngle,
                            sweepAngle = moderateAngle,
                            useCenter = false,
                            style = Stroke(width = strokeWidth),
                            topLeft = Offset((canvasSize - radius * 2) / 2, (canvasSize - radius * 2) / 2),
                            size = Size(radius * 2, radius * 2)
                        )
                        startAngle += moderateAngle
                    }

                    // Draw unhealthy segment
                    val unhealthyAngle = (categoryPercentages.unhealthy / total) * 360f * animatedProgress.value
                    if (unhealthyAngle > 0) {
                        drawArc(
                            color = AlertRed,
                            startAngle = startAngle,
                            sweepAngle = unhealthyAngle,
                            useCenter = false,
                            style = Stroke(width = strokeWidth),
                            topLeft = Offset((canvasSize - radius * 2) / 2, (canvasSize - radius * 2) / 2),
                            size = Size(radius * 2, radius * 2)
                        )
                    }
                }

                // Health score in center
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${(healthScore * animatedProgress.value).toInt()}",
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        color = getScoreColor(healthScore)
                    )
                    Text(
                        text = "Health Score",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Legend
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LegendItem(
                    color = HealthyGreen,
                    label = "Healthy",
                    percentage = categoryPercentages.healthy
                )
                LegendItem(
                    color = CautionOrange,
                    label = "Moderate",
                    percentage = categoryPercentages.moderate
                )
                LegendItem(
                    color = AlertRed,
                    label = "Unhealthy",
                    percentage = categoryPercentages.unhealthy
                )
            }
        }
    }
}

@Composable
private fun LegendItem(
    color: Color,
    label: String,
    percentage: Float
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(color, CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
        Text(
            text = "${percentage.toInt()}%",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

private fun getScoreColor(score: Int): Color {
    return when {
        score >= 70 -> HealthyGreen
        score >= 40 -> CautionOrange
        else -> AlertRed
    }
}

@ComponentPreview
@Composable
private fun PieChartSectionPreview() {
    FoodCheckAITheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Healthy product
            PieChartSection(
                categoryPercentages = PreviewData.healthyCategoryPercentages,
                healthScore = 85
            )

            // Moderate product
            PieChartSection(
                categoryPercentages = PreviewData.moderateCategoryPercentages,
                healthScore = 55
            )

            // Unhealthy product
            PieChartSection(
                categoryPercentages = PreviewData.unhealthyCategoryPercentages,
                healthScore = 25
            )
        }
    }
}
