package com.example.foodcheckai.presentation.home

data class HomeUiState(
    val scanCount: Int = 0,
    val isLoading: Boolean = false,
    val showHowItWorksDialog: Boolean = false
)
