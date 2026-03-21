package com.example.flavyo.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.OrderResponse
import com.example.flavyo.data.RetrofitClient
import com.example.flavyo.ui.theme.Poppins
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPendingOrdersScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var orders by remember { mutableStateOf<List<OrderResponse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    
    // State for Delete Confirmation Dialog
    var orderToDelete by remember { mutableStateOf<OrderResponse?>(null) }

    val fetchPending: suspend () -> Unit = {
        isLoading = true
        try {
            val response = RetrofitClient.authApi.getFilteredOrders(filter = mapOf("statusFilter" to "Pending"))
            if (response.isSuccessful) {
                orders = response.body()?.reversed() ?: emptyList()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Sync Error: Check Spreadsheet Columns", Toast.LENGTH_LONG).show()
        } finally {
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        fetchPending()
    }

    // Delete Confirmation Dialog
    if (orderToDelete != null) {
        AlertDialog(
            onDismissRequest = { orderToDelete = null },
            title = { Text("Confirm Deletion", fontFamily = Poppins, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Are you sure you want to delete this order?", fontFamily = Poppins)
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Customer: ${orderToDelete?.customerName}", fontWeight = FontWeight.Bold, fontSize = 14.sp, fontFamily = Poppins)
                            Text("ID: #${orderToDelete?.id?.take(8)}", fontSize = 12.sp, fontFamily = Poppins)
                            Text("Total: Rs. ${orderToDelete?.totalAmount?.toInt()}", fontSize = 12.sp, fontFamily = Poppins)
                            Text("Items: ${orderToDelete?.itemsString}", fontSize = 12.sp, fontFamily = Poppins)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val id = orderToDelete?.id ?: ""
                        scope.launch {
                            try {
                                val response = RetrofitClient.authApi.deleteOrder(params = mapOf("orderId" to id))
                                if (response.isSuccessful) {
                                    Toast.makeText(context, "Order Deleted", Toast.LENGTH_SHORT).show()
                                    fetchPending()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show()
                            } finally {
                                orderToDelete = null
                            }
                        }
                    }
                ) {
                    Text("Delete", color = Color.Red, fontWeight = FontWeight.Bold, fontFamily = Poppins)
                }
            },
            dismissButton = {
                TextButton(onClick = { orderToDelete = null }) {
                    Text("Cancel", fontFamily = Poppins)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pending Orders", fontFamily = Poppins, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF8F9FF))) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFFC1272D))
            } else if (orders.isEmpty()) {
                Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("No pending orders", fontFamily = Poppins, color = Color.Gray)
                    TextButton(onClick = { scope.launch { fetchPending() } }) {
                        Text("Refresh List", color = Color(0xFFC1272D))
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(orders, key = { it.id ?: UUID.randomUUID().toString() }) { order ->
                        AdminOrderItemCard(
                            order = order,
                            onAccept = { scope.launch { updateStatus(order.id ?: "", "Accepted", context, fetchPending) } },
                            onCancel = { scope.launch { updateStatus(order.id ?: "", "Cancelled", context, fetchPending) } },
                            onDelete = { orderToDelete = order }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdminOrderItemCard(
    order: OrderResponse,
    onAccept: () -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = order.customerName ?: "Customer",
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Text(text = "Order ID: #${order.id?.take(8)}", fontSize = 12.sp, color = Color.LightGray, fontFamily = Poppins)
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Rs. ${order.totalAmount?.toInt() ?: 0}",
                        color = Color(0xFFC1272D),
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            
            Text(text = order.userEmail ?: "", fontSize = 12.sp, color = Color.Gray, fontFamily = Poppins)
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)
            Text(text = "Items:", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, fontFamily = Poppins)
            Text(text = order.itemsString ?: "", fontSize = 14.sp, color = Color.DarkGray, fontFamily = Poppins)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red)
                ) {
                    Text("Cancel", fontFamily = Poppins)
                }
                Button(
                    onClick = onAccept,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("Accept", fontFamily = Poppins, color = Color.White)
                }
            }
        }
    }
}

suspend fun updateStatus(orderId: String, status: String, context: android.content.Context, onFinished: suspend () -> Unit) {
    try {
        val response = RetrofitClient.authApi.updateOrderStatus(params = mapOf("orderId" to orderId, "newStatus" to status))
        if (response.isSuccessful) {
            Toast.makeText(context, "Order $status Successfully", Toast.LENGTH_SHORT).show()
            onFinished()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
    }
}
