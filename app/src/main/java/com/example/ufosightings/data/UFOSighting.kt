package com.example.ufosightings.data

import java.util.Date

// Data class for UFO sighting
data class UFOSighting(
    val id: Long,
    val date: Date,
    val type: SightingType,
    val speed: Int
)

// Enum for sighting types
enum class SightingType(val displayName: String) {
    BLOB("Blob"),
    LAMP_SHADE("Lamp Shade")
}