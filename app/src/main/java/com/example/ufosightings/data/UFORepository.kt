package com.example.ufosightings.data


import com.example.ufosightings.data.UFODataGenerator
import com.example.ufosightings.data.UFOSighting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Repository class for managing UFO sightings data
 * Handles in-memory storage and business logic
 */
class UFORepository {

    private val _sightings = MutableStateFlow(UFODataGenerator.generateInitialSightings())
    val sightings: StateFlow<List<UFOSighting>> = _sightings.asStateFlow()

    /**
     * Adds a new random UFO sighting to the list
     */
    fun addRandomSighting() {
        val newSighting = UFODataGenerator.generateRandomSighting()
        val currentSightings = _sightings.value.toMutableList()
        currentSightings.add(0, newSighting) // Add to beginning of list
        _sightings.value = currentSightings
    }

    /**
     * Removes a UFO sighting by ID
     */
    fun removeSighting(sightingId: Long) {
        val updatedSightings = _sightings.value.filter { it.id != sightingId }
        _sightings.value = updatedSightings
    }

    /**
     * Gets a specific sighting by ID
     */
    fun getSightingById(sightingId: Long): UFOSighting? {
        return _sightings.value.find { it.id == sightingId }
    }

    /**
     * Gets the current count of sightings
     */
    fun getSightingsCount(): Int {
        return _sightings.value.size
    }
}