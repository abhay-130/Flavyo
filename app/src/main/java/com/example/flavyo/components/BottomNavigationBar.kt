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

@Composable
fun BottomNavigationBar(currentScreen: String, onScreenSelected: (String) -> Unit) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(selected = currentScreen == "home", onClick = { onScreenSelected("home") }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("Home") })
        NavigationBarItem(selected = currentScreen == "cart", onClick = { onScreenSelected("cart") }, icon = { Icon(Icons.Default.ShoppingCart, null) }, label = { Text("Cart") })
        NavigationBarItem(selected = currentScreen == "search", onClick = { onScreenSelected("search") }, icon = { Icon(Icons.Default.Search, null) }, label = { Text("Search") })
        NavigationBarItem(selected = currentScreen == "history", onClick = { onScreenSelected("history") }, icon = { Icon(Icons.AutoMirrored.Filled.List, null) }, label = { Text("History") })
        NavigationBarItem(selected = currentScreen == "profile", onClick = { onScreenSelected("profile") }, icon = { Icon(Icons.Default.Person, null) }, label = { Text("Profile") })
    }
}
