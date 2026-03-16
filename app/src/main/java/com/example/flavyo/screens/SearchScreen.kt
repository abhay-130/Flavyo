package com.example.flavyo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.IceCreamData
import com.example.flavyo.components.IceCreamItem

@Composable
fun SearchScreen(
    iceCreamList: List<IceCreamData>,
    cartItems: Map<String, Int>,
    onUpdateCart: (String, Int) -> Unit,
    onItemClick: (IceCreamData) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredList = if (searchQuery.isEmpty()) {
        iceCreamList
    } else {
        iceCreamList.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Column(modifier = Modifier.padding(horizontal = 24.dp).fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth().background(Color(0xFFE8F5E9), RoundedCornerShape(16.dp)),
            placeholder = { Text("What do you want to order?") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent)
        )
        Text("Menu", modifier = Modifier.padding(vertical = 20.dp).align(Alignment.CenterHorizontally),
            fontFamily = androidx.compose.ui.text.font.FontFamily.Serif, fontSize = 24.sp)
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(filteredList) { item ->
                IceCreamItem(
                    data = item,
                    count = cartItems[item.id] ?: 0,
                    onCountChange = { newCount -> onUpdateCart(item.id, newCount) },
                    onItemClick = { onItemClick(item) }
                )
            }
        }
    }
}
