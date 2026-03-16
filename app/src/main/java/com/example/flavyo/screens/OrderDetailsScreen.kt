package com.example.flavyo.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalShipping
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
    onOrderPlaced: (Double, String) -> Unit
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
        modifier = Modifier.fillMaxSize().background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(top = 40.dp)) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.background(Color(0xFFFFEEBC), CircleShape).size(40.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color(0xFFC69C2C))
            }
        }

        Text(
            text = "Order Details",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFC1272D),
            fontFamily = Poppins,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 32.dp)
        )

        OrderField(label = "Name", value = userPreferences.userName ?: "N/A")
        OrderField(label = "Address", value = userPreferences.userAddress ?: "N/A")
        OrderField(label = "Phone", value = userPreferences.userEmail ?: "N/A")

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFF0F0F0))
        ) {
            Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Total Amount", color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), fontFamily = Poppins)
                Text("Rs. ${totalAmount.toInt()}", color = Color(0xFFC1272D), fontSize = 22.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFF0F0F0))
        ) {
            Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Payment Method", color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), fontFamily = Poppins)
                Icon(Icons.Default.LocalShipping, null, tint = Color.Red)
                Text(" COD", fontWeight = FontWeight.Bold, fontFamily = Poppins)
            }
        }


        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onOrderPlaced(totalAmount, cartSummary) },
            modifier = Modifier.fillMaxWidth().height(120.dp).padding(bottom = 50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(8.dp)
        ) {
            Text("Place My Order", color = Color(0xFFC1272D), fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
        }
    }
}

@Composable
fun OrderField(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF0F0F0))
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(label, fontWeight = FontWeight.Bold, modifier = Modifier.width(80.dp), fontSize = 18.sp, fontFamily = Poppins)
            Text(value, color = Color.Gray, fontSize = 14.sp, fontFamily = Poppins)
        }
    }
}
