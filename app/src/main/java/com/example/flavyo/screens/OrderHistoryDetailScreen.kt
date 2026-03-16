package com.example.flavyo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.components.IceCreamItem
import com.example.flavyo.data.IceCreamData
import com.example.flavyo.data.Order
import com.example.flavyo.ui.theme.Poppins
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OrderHistoryDetailScreen(
    order: Order,
    allIceCreams: List<IceCreamData>,
    onBack: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    val dateString = dateFormat.format(Date(order.timestamp))

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF8F9FF)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // --- 1. THE SCROLLABLE CONTENT ---
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                // top = 160.dp ensures the first card starts below the fixed header
                contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 160.dp, bottom = 140.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // ... (Order Info Card, Items Ordered Header, and Items loop remain the same)
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Order Info", fontWeight = FontWeight.Bold, fontSize = 18.sp, fontFamily = Poppins)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "ID: #${order.id}", color = Color.Gray, fontSize = 12.sp, fontFamily = Poppins)
                            Text(text = "Date: $dateString", color = Color.Gray, fontSize = 14.sp, fontFamily = Poppins)
                            Text(text = "Status: Delivered", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold, fontSize = 14.sp, fontFamily = Poppins)
                        }
                    }
                }

                item {
                    Text(text = "Items Ordered", fontWeight = FontWeight.Bold, fontSize = 18.sp, fontFamily = Poppins, modifier = Modifier.padding(top = 8.dp))
                }

                val orderedItems = order.items.mapNotNull { (id, count) ->
                    allIceCreams.find { it.id == id }?.let { it to count }
                }

                items(orderedItems) { (iceCream, count) ->
                    IceCreamItem(data = iceCream, count = count, onCountChange = {}, isRecent = true)
                }
            }

            // --- 2. FIXED HEADER (With Solid Background) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8F9FF)) // Matches the screen background so scrolling content "disappears"
                    .padding(top = 75.dp, bottom = 15.dp) // Added bottom padding for a cleaner look
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(40.dp)
                        .background(Color.LightGray, CircleShape)
                        .align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFFC1272D)
                    )
                }

                Text(
                    text = "Order Details",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // --- 3. FIXED TOTAL PAID CARD ---
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 40.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFC1272D)),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Paid", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp, fontFamily = Poppins)
                    Text("Rs. ${order.totalAmount}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 25.sp, fontFamily = Poppins)
                }
            }
        }
    }
}
