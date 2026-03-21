package com.example.flavyo.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.AuthRequest
import com.example.flavyo.data.OrderResponse
import com.example.flavyo.data.RetrofitClient
import com.example.flavyo.ui.theme.Poppins
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminOrdersScreen(
    title: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var orders by remember { mutableStateOf<List<OrderResponse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val statusFilter = when (title) {
        "Pending Orders" -> "Pending"
        "Completed Orders" -> "Accepted"
        "Cancel Orders" -> "Cancelled"
        else -> null
    }

    val fetchOrders: suspend () -> Unit = {
        isLoading = true
        try {
            val response = if (statusFilter != null) {
                RetrofitClient.authApi.getFilteredOrders(filter = mapOf("statusFilter" to statusFilter))
            } else {
                RetrofitClient.authApi.getAllOrders(creds = AuthRequest())
            }
            
            if (response.isSuccessful) {
                orders = response.body() ?: emptyList()
            } else {
                Toast.makeText(context, "Server Error: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        } finally {
            isLoading = false
        }
    }

    LaunchedEffect(title) {
        fetchOrders()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, fontFamily = Poppins, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).background(Color.White)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFFC1272D))
            } else if (orders.isEmpty()) {
                Text("No orders found", modifier = Modifier.align(Alignment.Center), fontFamily = Poppins)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(orders) { order ->
                        AdminOrderItem(
                            order = order, 
                            isPending = statusFilter == "Pending",
                            onStatusUpdate = { newStatus ->
                                scope.launch {
                                    try {
                                        val response = RetrofitClient.authApi.updateOrderStatus(
                                            params = mapOf("orderId" to (order.id ?: ""), "newStatus" to newStatus)
                                        )
                                        if (response.isSuccessful && response.body()?.status == "success") {
                                            Toast.makeText(context, "Order $newStatus", Toast.LENGTH_SHORT).show()
                                            fetchOrders()
                                        } else {
                                            Toast.makeText(context, "Failed: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Failed to update: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdminOrderItem(
    order: OrderResponse, 
    isPending: Boolean,
    onStatusUpdate: (String) -> Unit
) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    val timestampLong = order.timestamp?.toLongOrNull() ?: 0L
    val dateString = dateFormat.format(Date(timestampLong))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0ED)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Showing Customer Name as the primary ID
                Text(
                    text = order.customerName ?: "Customer",
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = "Rs. ${order.totalAmount}",
                    color = Color(0xFFC1272D),
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Email: ${order.userEmail}", fontSize = 12.sp, fontFamily = Poppins, color = Color.Gray)
            Text(text = "Order ID: #${order.id?.take(8)}", fontSize = 12.sp, fontFamily = Poppins, color = Color.LightGray)
            Text(text = "Date: $dateString", fontSize = 12.sp, fontFamily = Poppins, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Items: ${order.itemsString}", fontSize = 14.sp, fontFamily = Poppins)
            
            if (isPending) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onStatusUpdate("Accepted") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Accept", color = Color.White, fontFamily = Poppins)
                    }
                    Button(
                        onClick = { onStatusUpdate("Cancelled") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Cancel", color = Color.White, fontFamily = Poppins)
                    }
                }
            }
        }
    }
}
