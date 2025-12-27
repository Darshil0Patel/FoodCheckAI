package com.example.foodcheckai.presentation.result.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EggAlt
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.foodcheckai.data.model.ProductCategory
import com.example.foodcheckai.presentation.preview.ComponentPreview
import com.example.foodcheckai.presentation.theme.AlertRed
import com.example.foodcheckai.presentation.theme.CautionOrange
import com.example.foodcheckai.presentation.theme.FoodCheckAITheme
import com.example.foodcheckai.presentation.theme.HealthyGreen
import com.example.foodcheckai.presentation.theme.InfoBlue

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryBadges(
    productCategory: ProductCategory,
    containsEgg: Boolean,
    containsDairy: Boolean,
    containsMeat: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Main category badge
        CategoryBadge(
            icon = when (productCategory) {
                ProductCategory.VEGETARIAN -> Icons.Default.LocalFlorist
                ProductCategory.VEGAN -> Icons.Default.LocalFlorist
                ProductCategory.NON_VEGETARIAN -> Icons.Default.Restaurant
                ProductCategory.UNKNOWN -> Icons.Default.CheckCircle
            },
            label = when (productCategory) {
                ProductCategory.VEGETARIAN -> "Vegetarian"
                ProductCategory.VEGAN -> "Vegan"
                ProductCategory.NON_VEGETARIAN -> "Non-Vegetarian"
                ProductCategory.UNKNOWN -> "Category Unknown"
            },
            backgroundColor = when (productCategory) {
                ProductCategory.VEGETARIAN -> HealthyGreen
                ProductCategory.VEGAN -> HealthyGreen
                ProductCategory.NON_VEGETARIAN -> AlertRed
                ProductCategory.UNKNOWN -> Color.Gray
            },
            large = true
        )

        // Allergen badges
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AllergenBadge(
                icon = Icons.Default.EggAlt,
                label = "Egg",
                contains = containsEgg,
                color = CautionOrange
            )
            AllergenBadge(
                icon = Icons.Default.WaterDrop,
                label = "Dairy",
                contains = containsDairy,
                color = InfoBlue
            )
            AllergenBadge(
                icon = Icons.Default.Restaurant,
                label = "Meat",
                contains = containsMeat,
                color = AlertRed
            )
        }
    }
}

@Composable
private fun CategoryBadge(
    icon: ImageVector,
    label: String,
    backgroundColor: Color,
    large: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = backgroundColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = if (large) 20.dp else 16.dp,
                vertical = if (large) 16.dp else 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(if (large) 28.dp else 20.dp),
            tint = backgroundColor
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            style = if (large) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = backgroundColor
        )
    }
}

@Composable
private fun AllergenBadge(
    icon: ImageVector,
    label: String,
    contains: Boolean,
    color: Color
) {
    Row(
        modifier = Modifier
            .background(
                color = if (contains) color.copy(alpha = 0.15f) else Color.Gray.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(18.dp),
            tint = if (contains) color else Color.Gray
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = if (contains) "Contains $label" else "No $label",
            style = MaterialTheme.typography.labelMedium,
            color = if (contains) color else Color.Gray,
            fontWeight = if (contains) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@ComponentPreview
@Composable
private fun CategoryBadgesPreview() {
    FoodCheckAITheme {
        Surface(color = Color.White) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Vegan
                CategoryBadges(
                    productCategory = ProductCategory.VEGAN,
                    containsEgg = false,
                    containsDairy = false,
                    containsMeat = false
                )

                // Vegetarian with allergens
                CategoryBadges(
                    productCategory = ProductCategory.VEGETARIAN,
                    containsEgg = true,
                    containsDairy = true,
                    containsMeat = false
                )

                // Non-vegetarian
                CategoryBadges(
                    productCategory = ProductCategory.NON_VEGETARIAN,
                    containsEgg = true,
                    containsDairy = true,
                    containsMeat = true
                )
            }
        }
    }
}
