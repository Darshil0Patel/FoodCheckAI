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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.foodcheckai.data.model.HealthImpact
import com.example.foodcheckai.data.model.IngredientCategory
import com.example.foodcheckai.data.model.IngredientInfo
import com.example.foodcheckai.presentation.preview.ComponentPreview
import com.example.foodcheckai.presentation.preview.PreviewData
import com.example.foodcheckai.presentation.theme.AlertRed
import com.example.foodcheckai.presentation.theme.CautionOrange
import com.example.foodcheckai.presentation.theme.FoodCheckAITheme
import com.example.foodcheckai.presentation.theme.HealthyGreen
import com.example.foodcheckai.presentation.theme.InfoBlue

@Composable
fun IngredientsTable(
    ingredients: List<IngredientInfo>,
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
                .padding(20.dp)
        ) {
            Text(
                text = "Detailed Ingredients",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            ingredients.forEachIndexed { index, ingredient ->
                IngredientRow(ingredient = ingredient)

                if (index < ingredients.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = Color.Black.copy(alpha = 0.1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun IngredientRow(ingredient: IngredientInfo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Category icon
        Icon(
            imageVector = getCategoryIcon(ingredient.category),
            contentDescription = ingredient.category.name,
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = getCategoryColor(ingredient.category).copy(alpha = 0.15f),
                    shape = CircleShape
                )
                .padding(8.dp),
            tint = getCategoryColor(ingredient.category)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = ingredient.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                // Health impact badge
                HealthImpactBadge(healthImpact = ingredient.healthImpact)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = ingredient.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Category label
            Text(
                text = ingredient.category.name.lowercase().replace('_', ' ').capitalize(),
                style = MaterialTheme.typography.labelSmall,
                color = getCategoryColor(ingredient.category),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun HealthImpactBadge(healthImpact: HealthImpact) {
    val (icon, color, text) = when (healthImpact) {
        HealthImpact.POSITIVE -> Triple(Icons.Default.Favorite, HealthyGreen, "Good")
        HealthImpact.NEUTRAL -> Triple(Icons.Default.CheckCircle, CautionOrange, "OK")
        HealthImpact.NEGATIVE -> Triple(Icons.Default.ErrorOutline, AlertRed, "Bad")
    }

    Row(
        modifier = Modifier
            .background(
                color = color.copy(alpha = 0.15f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(14.dp),
            tint = color
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun getCategoryIcon(category: IngredientCategory): ImageVector {
    return when (category) {
        IngredientCategory.HEALTHY -> Icons.Default.Favorite
        IngredientCategory.MODERATE -> Icons.Default.CheckCircle
        IngredientCategory.UNHEALTHY -> Icons.Default.Warning
        IngredientCategory.ALLERGEN -> Icons.Default.ErrorOutline
        IngredientCategory.PRESERVATIVE -> Icons.Default.Science
        IngredientCategory.ADDITIVE -> Icons.Default.Add
        IngredientCategory.NATURAL -> Icons.Default.Favorite
    }
}

private fun getCategoryColor(category: IngredientCategory): Color {
    return when (category) {
        IngredientCategory.HEALTHY -> HealthyGreen
        IngredientCategory.MODERATE -> CautionOrange
        IngredientCategory.UNHEALTHY -> AlertRed
        IngredientCategory.ALLERGEN -> AlertRed
        IngredientCategory.PRESERVATIVE -> InfoBlue
        IngredientCategory.ADDITIVE -> CautionOrange
        IngredientCategory.NATURAL -> HealthyGreen
    }
}

private fun String.capitalize(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

@ComponentPreview
@Composable
private fun IngredientsTablePreview() {
    FoodCheckAITheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IngredientsTable(ingredients = PreviewData.sampleIngredients)

            // Empty state
            IngredientsTable(ingredients = emptyList())
        }
    }
}

@ComponentPreview
@Composable
private fun HealthImpactBadgePreview() {
    FoodCheckAITheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HealthImpactBadge(healthImpact = HealthImpact.POSITIVE)
            HealthImpactBadge(healthImpact = HealthImpact.NEUTRAL)
            HealthImpactBadge(healthImpact = HealthImpact.NEGATIVE)
        }
    }
}
