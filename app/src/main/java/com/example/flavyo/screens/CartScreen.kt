package com.example.flavyo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun CartScreen(
    cartItems: List<IceCreamData>,
    onUpdateCart: (String, Int) -> Unit,
    onCheckout: (Double, String) -> Unit,
    onItemClick: (IceCreamData) -> Unit
) {
    // Group items to show count (e.g., "2x Chocolate")
    val groupedItems = cartItems.groupBy { it.id }

    val totalAmount = cartItems.sumOf { it.price.toDoubleOrNull() ?: 0.0 }

    // Create a summary string for the database (e.g., "2x Vanilla, 1x Mango")
    val orderSummary = groupedItems.values.joinToString(", ") { items ->
        "${items.size}x ${items.first().name}"
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
        Text(
            text = "My Cart",
            modifier = Modifier
                .padding(top = 40.dp, bottom = 10.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins
        )

        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("Your cart is empty", color = Color.Gray, fontFamily = Poppins)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                // Show unique items in the list
                items(groupedItems.keys.toList()) { itemId ->
                    val item = groupedItems[itemId]?.first()!!
                    val count = groupedItems[itemId]?.size ?: 0

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

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total Amount", fontSize = 18.sp, fontFamily = Poppins, color = Color.Black)
                Text("Rs. $totalAmount", fontSize = 22.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins, color = Color(0xFFC1272D))
            }

            Button(
                onClick = { onCheckout(totalAmount, orderSummary) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC1272D)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Place My Order", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
            }
        }
    }
}
