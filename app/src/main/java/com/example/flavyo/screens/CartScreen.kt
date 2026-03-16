package com.example.flavyo.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.components.IceCreamItem
import com.example.flavyo.data.*
import com.example.flavyo.ui.theme.Poppins
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun CartContent(
    allIceCreams: List<IceCreamData>,
    cartItems: Map<String, Int>,
    onUpdateCart: (String, Int) -> Unit,
    onItemClick: (IceCreamData) -> Unit,
    onOrderSuccess: () -> Unit // Navigation callback
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userPreferences = remember { UserPreferences(context) }

    // Filter items actually in the cart
    val itemsInCart = allIceCreams.filter { cartItems.containsKey(it.id) && (cartItems[it.id] ?: 0) > 0 }

    // Calculate total price
    val totalAmount = itemsInCart.sumOf { item ->
        val count = cartItems[item.id] ?: 0
        (item.price.toDoubleOrNull() ?: 0.0) * count
    }

    var isLoading by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {

        Text(
            text = "My Cart",
            modifier = Modifier
                .padding(top = 20.dp, bottom = 10.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins
        )

        if (itemsInCart.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("Your cart is empty", color = Color.Gray, fontFamily = Poppins)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                items(itemsInCart) { item ->
                    IceCreamItem(
                        data = item,
                        count = cartItems[item.id] ?: 0,
                        onCountChange = { newCount -> onUpdateCart(item.id, newCount) },
                        onItemClick = { onItemClick(item) }
                    )
                }
            }

            // Total Summary Row
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total Amount", fontSize = 18.sp, fontFamily = Poppins, color = Color.Gray)
                Text("Rs. $totalAmount", fontSize = 22.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins, color = Color(0xFFC1272D))
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 40.dp),
                color = Color(0xFFC1272D)
            )
        } else {
            Button(
                onClick = {
                        // Just navigate to the success/confirmation screen
                        // We pass the total and items through navigation or a ViewModel
                        onOrderSuccess()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
                    .height(56.dp),
                enabled = itemsInCart.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC1272D)),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(4.dp)
            ) {
                Text("Place My Order", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
            }
        }
    }
}