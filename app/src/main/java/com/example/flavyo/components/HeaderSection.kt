package com.example.flavyo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderSection(
    hasNotification: Boolean,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Explore Your Favorite Ice Creams",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(250.dp)
        )
        
        Box(
            modifier = Modifier.size(32.dp)
        ) {
            IconButton(
                onClick = onNotificationClick,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = Color(0xFFC1272D),
                    modifier = Modifier.size(32.dp)
                )
            }
            
            if (hasNotification) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .align(Alignment.TopEnd)
                        .background(Color(0xFF4CAF50), CircleShape)
                )
            }
        }
    }
}
