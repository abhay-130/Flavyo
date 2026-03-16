package com.example.flavyo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.OrderResponse
import com.example.flavyo.data.RetrofitClient
import com.example.flavyo.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedOrdersScreen(onBack: () -> Unit) {
    var orders by remember { mutableStateOf<List<OrderResponse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.authApi.getFilteredOrders(filter = mapOf("statusFilter" to "Accepted"))
            if (response.isSuccessful) {
                orders = response.body() ?: emptyList()
            }
        } catch (e: Exception) {
            // Handle error
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Completed Orders", fontFamily = Poppins, fontWeight = FontWeight.Bold) },
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
                Text("No completed orders found", modifier = Modifier.align(Alignment.Center), fontFamily = Poppins)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(orders) { order ->
                        AdminOrderItem(
                            order = order, 
                            isPending = false,
                            onStatusUpdate = {}
                        )
                    }
                }
            }
        }
    }
}
