package com.example.flavyo.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Explore Your Favorite Ice Creams", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(250.dp))
        Icon(Icons.Outlined.Notifications, null, tint = Color(0xFF4CAF50), modifier = Modifier.size(32.dp))
    }
}
