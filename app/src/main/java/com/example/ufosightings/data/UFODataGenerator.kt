package com.example.ufosightings.data

import com.example.ufosightings.data.SightingType
import com.example.ufosightings.data.UFOSighting
import java.util.*
import kotlin.random.Random

/**
 * Utility class for generating UFO sighting data
 */
object UFODataGenerator {

    /**
     * Generates the initial list of UFO sightings matching the design spec
     */
    fun generateInitialSightings(): List<UFOSighting> {
        return listOf(
            UFOSighting(
                id = 1L,
                date = createDate(2020, 0, 25, 7, 30), // January 25, 2020 7:30 AM
                type = SightingType.LAMP_SHADE,
                speed = 14
            ),
            UFOSighting(
                id = 2L,
                date = createDate(2020, 0, 20, 21, 11), // January 20, 2020 9:11 PM
                type = SightingType.BLOB,
                speed = 3
            ),
            UFOSighting(
                id = 3L,
                date = createDate(2020, 0, 25, 7, 30), // January 25, 2020 7:30 AM
                type = SightingType.BLOB,
                speed = 3
            ),
            UFOSighting(
                id = 4L,
                date = createDate(2020, 0, 25, 7, 30), // January 25, 2020 7:30 AM
                type = SightingType.LAMP_SHADE,
                speed = 14
            ),
            UFOSighting(
                id = 5L,
                date = createDate(2020, 0, 25, 7, 30), // January 25, 2020 7:30 AM
                type = SightingType.BLOB,
                speed = 3
            )
        )
    }

    /**
     * Generates a random UFO sighting with random attributes
     */
    fun generateRandomSighting(): UFOSighting {
        val types = SightingType.values()
        val randomType = types[Random.nextInt(types.size)]
        val randomSpeed = Random.nextInt(1, 51) // 1-50 knots

        // Generate random date within last year
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -Random.nextInt(365))
        calendar.set(Calendar.HOUR_OF_DAY, Random.nextInt(24))
        calendar.set(Calendar.MINUTE, Random.nextInt(60))
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return UFOSighting(
            id = System.currentTimeMillis() + Random.nextLong(1000),
            date = calendar.time,
            type = randomType,
            speed = randomSpeed
        )
    }

    /**
     * Helper function to create a Date object with specific parameters
     */
    private fun createDate(year: Int, month: Int, day: Int, hour: Int, minute: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
}