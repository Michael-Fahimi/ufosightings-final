package com.example.ufosightings.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ufosightings.data.UFOSighting
import com.example.ufosightings.data.SightingType
import java.text.SimpleDateFormat
import java.util.*

/**
 * Composable for displaying a single UFO sighting item in the list
 */
@Composable
fun UFOSightingItem(
    sighting: UFOSighting,
    isSelected: Boolean,
    themeColor: Color,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("h:mm a", Locale.getDefault())

    Column(modifier = modifier) {
        // Main item row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isSelected) Color(0xFFE3F2FD) else Color.White)
                .clickable { onClick() }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // UFO Type Icon
            UFOTypeIcon(
                sightingType = sighting.type,
                themeColor = themeColor,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Sighting Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = dateFormatter.format(sighting.date),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = "${sighting.speed} knots · ${sighting.type.displayName}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Time
            Text(
                text = timeFormatter.format(sighting.date),
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // Delete button (shown when selected)
        if (isSelected) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE3F2FD))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onDelete,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Remove",
                        fontSize = 14.sp
                    )
                }
            }
        }

        // Divider (only show if not selected)
        if (!isSelected) {
            Divider(
                color = Color.Gray.copy(alpha = 0.2f),
                thickness = 0.5.dp,
                modifier = Modifier.padding(start = 72.dp)
            )
        }
    }
}

/**
 * Composable for displaying UFO type icons
 */
@Composable
fun UFOTypeIcon(
    sightingType: SightingType,
    themeColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(themeColor),
        contentAlignment = Alignment.Center
    ) {
        when (sightingType) {
            SightingType.LAMP_SHADE -> {
                // Simple lamp shade representation using emoji
                Text(
                    text = "🛸",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
            SightingType.BLOB -> {
                // Simple blob representation
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                )
            }
        }
    }
}