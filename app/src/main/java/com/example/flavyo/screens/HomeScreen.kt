package com.example.flavyo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.flavyo.components.BannerCarousel
import com.example.flavyo.components.IceCreamItem
import com.example.flavyo.components.PopularHeader
import com.example.flavyo.data.IceCreamData

@Composable
fun HomeContent(
    items: List<IceCreamData>,
    isLoading: Boolean,
    cartItems: Map<String, Int>,
    onUpdateCart: (String, Int) -> Unit,
    onItemClick: (IceCreamData) -> Unit
) {
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFFC1272D))
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item { BannerCarousel() }
            item { PopularHeader() }
            items(items) { item ->
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
