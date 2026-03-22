package com.example.flavyo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.components.IceCreamItem
import com.example.flavyo.data.IceCreamData
import com.example.flavyo.ui.theme.Poppins

@Composable
fun UserViewMenuScreen(
    iceCreamList: List<IceCreamData>,
    cartItems: Map<String, Int>,
    onUpdateCart: (String, Int) -> Unit,
    onItemClick: (IceCreamData) -> Unit,
    onBack: () -> Unit
) {
    // Logic to separate items by category
    val groupedItems = mutableListOf<Any>()
    iceCreamList.forEach { item ->
        if (item.id.isEmpty() || item.price.isEmpty()) {
            groupedItems.add(item.name) // Category Header
        } else {
            groupedItems.add(item) // Ice Cream Item
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FF)) // Entire background is grey-blue
    ) {
        // Custom Header to match Search Menu style and reduce padding
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFFC1272D)
                )
            }

            Text(
                text = "Menu",
                modifier = Modifier.align(Alignment.Center),
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
        }
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(groupedItems) { element ->
                when (element) {
                    is String -> {
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFC1272D))
                                .padding(horizontal = 24.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = element.uppercase(),
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = Poppins,
                                letterSpacing = 1.2.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    is IceCreamData -> {
                        IceCreamItem(
                            data = element,
                            count = cartItems[element.id] ?: 0,
                            onCountChange = { newCount -> onUpdateCart(element.id, newCount) },
                            onItemClick = { onItemClick(element) }
                        )
                    }
                }
            }
        }
    }
}
