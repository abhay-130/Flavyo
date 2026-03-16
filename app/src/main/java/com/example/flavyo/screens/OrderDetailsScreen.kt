package com.example.flavyo.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.IceCreamData
import com.example.flavyo.data.UserPreferences
import com.example.flavyo.ui.theme.Poppins

@Composable
fun OrderDetailsScreen(
    allIceCreams: List<IceCreamData>,
    cartItems: Map<String, Int>,
    onBack: () -> Unit,
    onProceedToPayment: (Double, String) -> Unit
) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    
    // Use the prices from allIceCreams to ensure correct total calculation
    val itemsInCart = allIceCreams.filter { cartItems.containsKey(it.id) }
    val totalAmount = itemsInCart.sumOf { item ->
        (item.price.toDoubleOrNull() ?: 0.0) * (cartItems[item.id] ?: 0)
    }
    val cartSummary = itemsInCart.joinToString(", ") { item ->
        "${cartItems[item.id]}x ${item.name}"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        // --- Header ---
        Box(modifier = Modifier.fillMaxWidth().padding(top = 50.dp)) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFFFEEBC), CircleShape)
                    .align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFFC69C2C)
                )
            }

            Text(
                text = "Order Details",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // --- Delivery Info Section ---
        Text(
            text = "Delivery Information",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        InfoCard(label = "Recipient", value = userPreferences.userName ?: "Add Name in Profile")
        InfoCard(label = "Address", value = userPreferences.userAddress ?: "Add Address in Profile")
        InfoCard(label = "Contact", value = userPreferences.userEmail ?: "N/A")

        Spacer(modifier = Modifier.height(24.dp))

        // --- Order Total Section ---
        Text(
            text = "Order Totals",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FF)),
            border = BorderStroke(1.dp, Color(0xFFEEEEEE))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Subtotal", color = Color.Gray, fontFamily = Poppins)
                    Text("Rs. ${totalAmount.toInt()}", fontWeight = FontWeight.Medium, fontFamily = Poppins)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Delivery Fee", color = Color.Gray, fontFamily = Poppins)
                    Text("FREE", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold, fontFamily = Poppins)
                }
                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE0E0E0))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total Amount", fontWeight = FontWeight.Bold, fontSize = 18.sp, fontFamily = Poppins)
                    Text("Rs. ${totalAmount.toInt()}", color = Color(0xFFC1272D), fontSize = 22.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // --- Action Button ---
        Button(
            onClick = { onProceedToPayment(totalAmount, cartSummary) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC1272D)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                "Proceed to Payment",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins
            )
        }
    }
}

@Composable
fun InfoCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF0F0F0))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = label, color = Color.Gray, fontSize = 12.sp, fontFamily = Poppins)
                Text(text = value, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, fontFamily = Poppins, color = Color.Black)
            }
        }
    }
}
