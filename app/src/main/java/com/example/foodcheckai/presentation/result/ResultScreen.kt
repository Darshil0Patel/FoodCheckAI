package com.example.foodcheckai.presentation.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodcheckai.presentation.preview.ComponentPreview
import com.example.foodcheckai.presentation.preview.CompletePreview
import com.example.foodcheckai.presentation.preview.PreviewData
import com.example.foodcheckai.presentation.result.components.AnalyzingAnimation
import com.example.foodcheckai.presentation.result.components.CategoryBadges
import com.example.foodcheckai.presentation.result.components.DisclaimerCard
import com.example.foodcheckai.presentation.result.components.HealthInsightsCard
import com.example.foodcheckai.presentation.result.components.IngredientsTable
import com.example.foodcheckai.presentation.result.components.PieChartSection
import com.example.foodcheckai.presentation.theme.AlertRed
import com.example.foodcheckai.presentation.theme.FoodCheckAITheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    ingredientText: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ResultViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(ingredientText) {
        if (ingredientText.isNotBlank()) {
            viewModel.analyzeIngredients(ingredientText)
        }
    }

    ResultScreenContent(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onRetry = { viewModel.retryAnalysis() },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResultScreenContent(
    uiState: ResultUiState,
    onNavigateBack: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Analysis Result",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    AnalyzingAnimation()
                }

                uiState.errorMessage != null -> {
                    ErrorContent(
                        errorMessage = uiState.errorMessage!!,
                        onRetry = onRetry
                    )
                }

                uiState.analysisResult != null -> {
                    val result = uiState.analysisResult!!

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))

                        // Category badges
                        CategoryBadges(
                            productCategory = result.productCategory,
                            containsEgg = result.containsEgg,
                            containsDairy = result.containsDairy,
                            containsMeat = result.containsMeat
                        )

                        // Pie chart with health score
                        PieChartSection(
                            categoryPercentages = result.categoryPercentages,
                            healthScore = result.healthScore
                        )

                        // Detailed ingredients table
                        IngredientsTable(
                            ingredients = result.ingredientBreakdown
                        )

                        // Health insights
                        HealthInsightsCard(
                            healthInsights = result.healthInsights
                        )

                        // Disclaimer
                        DisclaimerCard()

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorContent(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "⚠️",
                style = MaterialTheme.typography.displayLarge
            )

            Text(
                text = "Analysis Failed",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AlertRed
                )
            ) {
                Text("Retry Analysis")
            }
        }
    }
}

// ===== Previews =====

@CompletePreview
@Composable
private fun ResultScreenLoadingPreview() {
    FoodCheckAITheme {
        ResultScreenContent(
            uiState = PreviewData.resultUiStateLoading,
            onNavigateBack = { },
            onRetry = { }
        )
    }
}

@ComponentPreview
@Composable
private fun ResultScreenVeganPreview() {
    FoodCheckAITheme {
        ResultScreenContent(
            uiState = PreviewData.resultUiStateVegan,
            onNavigateBack = { },
            onRetry = { }
        )
    }
}

@ComponentPreview
@Composable
private fun ResultScreenVegetarianPreview() {
    FoodCheckAITheme {
        ResultScreenContent(
            uiState = PreviewData.resultUiStateSuccess,
            onNavigateBack = { },
            onRetry = { }
        )
    }
}

@ComponentPreview
@Composable
private fun ResultScreenNonVegPreview() {
    FoodCheckAITheme {
        ResultScreenContent(
            uiState = PreviewData.resultUiStateNonVeg,
            onNavigateBack = { },
            onRetry = { }
        )
    }
}

@ComponentPreview
@Composable
private fun ResultScreenErrorPreview() {
    FoodCheckAITheme {
        ResultScreenContent(
            uiState = PreviewData.resultUiStateError,
            onNavigateBack = { },
            onRetry = { }
        )
    }
}

@ComponentPreview
@Composable
private fun ErrorContentPreview() {
    FoodCheckAITheme {
        ErrorContent(
            errorMessage = "Failed to analyze ingredients. Please check your internet connection and try again.",
            onRetry = { }
        )
    }
}
