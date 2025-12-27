package com.example.foodcheckai.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodcheckai.data.local.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesManager = PreferencesManager.getInstance(application)

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadScanCount()
    }

    private fun loadScanCount() {
        viewModelScope.launch {
            preferencesManager.scanCountFlow.collect { count ->
                _uiState.update { it.copy(scanCount = count) }
            }
        }
    }

    fun onCheckIngredientsClick() {
        // Will be handled by navigation in HomeScreen
        // Future: Could add analytics or other logic here
    }

    fun onHowItWorksClick() {
        // Will be handled by navigation in HomeScreen
        // Future: Could show a dialog or navigate to a dedicated screen
    }

    fun incrementScanCount() {
        viewModelScope.launch {
            preferencesManager.incrementScanCount()
        }
    }
}
