package com.example.ufosightings.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ufosightings.viewmodel.UFOSightingsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Main screen composable for the UFO Sightings application
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UFOSightingsScreen(
    ufoViewModel: UFOSightingsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    // Collect state from ViewModel
    val sightings by ufoViewModel.sightings.collectAsState()
    val selectedSightingId by ufoViewModel.selectedSightingId.collectAsState()
    val uiState by ufoViewModel.uiState.collectAsState()

    // Theme colors
    val themeGreen = Color(0xFF08A462)
    val backgroundColor = Color(0xFFF8F8F8)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "UFO Sightings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            },
            actions = {
                IconButton(
                    onClick = { ufoViewModel.addRandomSighting() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add UFO Sighting",
                        tint = themeGreen
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        // Error message (if any)
        uiState.errorMessage?.let { errorMessage ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f))
            ) {
                Text(
                    text = errorMessage,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Red
                )
            }
        }

        // Loading indicator
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = themeGreen
                )
            }
        }

        // Sightings List
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = sightings,
                key = { sighting -> sighting.id }
            ) { sighting ->
                UFOSightingItem(
                    sighting = sighting,
                    isSelected = selectedSightingId == sighting.id,
                    themeColor = themeGreen,
                    onClick = {
                        ufoViewModel.selectSighting(sighting.id)
                    },
                    onDelete = {
                        ufoViewModel.removeSighting(sighting.id)
                    }
                )
            }
        }
    }

    // Clear error message after showing it
    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            kotlinx.coroutines.delay(3000) // Show error for 3 seconds
            ufoViewModel.clearError()
        }
    }
}