package com.example.foodcheckai.presentation.home.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.foodcheckai.presentation.preview.ComponentPreview
import com.example.foodcheckai.presentation.theme.FoodCheckAITheme
import com.example.foodcheckai.presentation.theme.HealthyGreen

@Composable
fun AnimatedFoodIcon(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    color: Color = HealthyGreen
) {
    val infiniteTransition = rememberInfiniteTransition(label = "food_icon_animation")

    // Floating animation (up and down)
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset_animation"
    )

    // Subtle rotation animation
    val rotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation_animation"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.LocalDining,
            contentDescription = "Food Icon",
            modifier = Modifier
                .size(size)
                .offset(y = offsetY.dp)
                .rotate(rotation),
            tint = color
        )
    }
}

@ComponentPreview
@Composable
private fun AnimatedFoodIconPreview() {
    FoodCheckAITheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                AnimatedFoodIcon(size = 50.dp)
                AnimatedFoodIcon(size = 100.dp)
                AnimatedFoodIcon(
                    size = 80.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
