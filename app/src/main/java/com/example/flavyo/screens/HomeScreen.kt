package com.example.flavyo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.flavyo.components.BannerCarousel
import com.example.flavyo.components.IceCreamItem
import com.example.flavyo.components.PopularHeader
import com.example.flavyo.data.IceCreamData
import com.example.flavyo.components.HeaderSection

@Composable
fun HomeScreen(
    iceCreamList: List<IceCreamData>,
    cartItems: Map<String, Int>,
    isLoading: Boolean,
    onUpdateCart: (String, Int) -> Unit,
    onItemClick: (IceCreamData) -> Unit,
    onNotificationClick: () -> Unit,
    hasNotification: Boolean
) {
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderSection(
            hasNotification = hasNotification,
            onNotificationClick = onNotificationClick
        )

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFC1272D))
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item { BannerCarousel() }
                item { PopularHeader() }
                items(iceCreamList) { item ->
                    val count = cartItems[item.id] ?: 0
                    IceCreamItem(
                        data = item,
                        count = count,
                        onCountChange = { newCount ->
                            onUpdateCart(item.id, newCount)
                        },
                        onItemClick = { onItemClick(item) }
                    )
                }
            }
        }
    }
}
