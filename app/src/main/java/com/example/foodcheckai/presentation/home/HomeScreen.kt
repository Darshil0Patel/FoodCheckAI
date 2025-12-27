package com.example.foodcheckai.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodcheckai.presentation.home.components.AnimatedFoodIcon
import com.example.foodcheckai.presentation.home.components.FeatureIcons
import com.example.foodcheckai.presentation.home.components.GradientBackground
import com.example.foodcheckai.presentation.home.components.PrimaryButton
import com.example.foodcheckai.presentation.home.components.StatsCard
import com.example.foodcheckai.presentation.preview.ComponentPreview
import com.example.foodcheckai.presentation.preview.CompletePreview
import com.example.foodcheckai.presentation.preview.PreviewData
import com.example.foodcheckai.presentation.theme.FoodCheckAITheme

@Composable
fun HomeScreen(
    onNavigateToInput: () -> Unit,
    onNavigateToHowItWorks: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onNavigateToInput = onNavigateToInput,
        onNavigateToHowItWorks = onNavigateToHowItWorks,
        modifier = modifier
    )
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onNavigateToInput: () -> Unit,
    onNavigateToHowItWorks: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        GradientBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Top spacing
                Spacer(modifier = Modifier.height(48.dp))

                // Animated Food Icon
                AnimatedFoodIcon(
                    size = 100.dp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // App Name
                Text(
                    text = "FoodCheck AI",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                // Tagline
                Text(
                    text = "Understand what's inside your food",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Stats Card
                if (uiState.scanCount > 0) {
                    StatsCard(scanCount = uiState.scanCount)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Primary CTA Button
                PrimaryButton(
                    text = "Check Ingredients",
                    onClick = onNavigateToInput
                )

                // Secondary descriptive text
                Text(
                    text = "Identify egg, dairy & dietary compatibility",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Feature Icons
                FeatureIcons()

                // Flexible spacer to push disclaimer to bottom
                Spacer(modifier = Modifier.weight(1f))

                // How it works disclaimer link
                TextButton(
                    onClick = onNavigateToHowItWorks
                ) {
                    Text(
                        text = "How this works",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// ===== Previews =====

@CompletePreview
@Composable
private fun HomeScreenDefaultPreview() {
    FoodCheckAITheme {
        HomeScreenContent(
            uiState = PreviewData.homeUiStateDefault,
            onNavigateToInput = { },
            onNavigateToHowItWorks = { }
        )
    }
}

@ComponentPreview
@Composable
private fun HomeScreenWithScansPreview() {
    FoodCheckAITheme {
        HomeScreenContent(
            uiState = PreviewData.homeUiStateWithScans,
            onNavigateToInput = { },
            onNavigateToHowItWorks = { }
        )
    }
}

@ComponentPreview
@Composable
private fun HomeScreenManyScansPreview() {
    FoodCheckAITheme {
        HomeScreenContent(
            uiState = PreviewData.homeUiStateWithManyScans,
            onNavigateToInput = { },
            onNavigateToHowItWorks = { }
        )
    }
}
