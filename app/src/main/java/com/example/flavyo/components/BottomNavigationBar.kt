package com.example.flavyo.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(
    currentScreen: String,
    onScreenSelected: (String) -> Unit,
    hasNotification: Boolean
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(selected = currentScreen == "Home", onClick = { onScreenSelected("Home") }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("Home") })
        NavigationBarItem(selected = currentScreen == "Cart", onClick = { onScreenSelected("Cart") }, icon = { Icon(Icons.Default.ShoppingCart, null) }, label = { Text("Cart") })
        NavigationBarItem(selected = currentScreen == "Search", onClick = { onScreenSelected("Search") }, icon = { Icon(Icons.Default.Search, null) }, label = { Text("Search") })
        NavigationBarItem(selected = currentScreen == "History", onClick = { onScreenSelected("History") }, icon = { Icon(Icons.AutoMirrored.Filled.List, null) }, label = { Text("History") })
        NavigationBarItem(selected = currentScreen == "Profile", onClick = { onScreenSelected("Profile") }, icon = { Icon(Icons.Default.Person, null) }, label = { Text("Profile") })
    }
}
