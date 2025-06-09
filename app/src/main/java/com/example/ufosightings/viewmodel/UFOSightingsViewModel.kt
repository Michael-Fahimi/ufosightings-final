package com.example.ufosightings.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufosightings.data.UFORepository
import com.example.ufosightings.data.UFOSighting
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for managing UFO sightings UI state and business logic
 */
class UFOSightingsViewModel(
    private val repository: UFORepository = UFORepository()
) : ViewModel() {

    // Expose sightings from repository
    val sightings: StateFlow<List<UFOSighting>> = repository.sightings

    // Track selected sighting ID
    private val _selectedSightingId = MutableStateFlow<Long?>(null)
    val selectedSightingId: StateFlow<Long?> = _selectedSightingId.asStateFlow()

    // UI state for loading, error handling, etc.
    private val _uiState = MutableStateFlow(UFOSightingsUiState())
    val uiState: StateFlow<UFOSightingsUiState> = _uiState.asStateFlow()

    /**
     * Adds a new random UFO sighting
     */
    fun addRandomSighting() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                repository.addRandomSighting()
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to add sighting: ${e.message}"
                )
            }
        }
    }

    /**
     * Removes a UFO sighting by ID
     */
    fun removeSighting(sightingId: Long) {
        viewModelScope.launch {
            try {
                repository.removeSighting(sightingId)
                // Clear selection if the removed item was selected
                if (_selectedSightingId.value == sightingId) {
                    _selectedSightingId.value = null
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to remove sighting: ${e.message}"
                )
            }
        }
    }

    /**
     * Selects or deselects a UFO sighting
     */
    fun selectSighting(sightingId: Long) {
        _selectedSightingId.value = if (_selectedSightingId.value == sightingId) {
            null // Deselect if already selected
        } else {
            sightingId // Select new sighting
        }
    }

    /**
     * Clears any error messages
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

/**
 * Data class representing the UI state
 */
data class UFOSightingsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)