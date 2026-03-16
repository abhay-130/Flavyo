package com.example.flavyo.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import com.example.flavyo.data.*
import com.example.flavyo.ui.theme.Poppins
import kotlinx.coroutines.launch

class AdminActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdminDashboard()
        }
    }
}

@Composable
fun AdminDashboard() {
    val scope = rememberCoroutineScope()
    var orders by remember { mutableStateOf<List<OrderResponse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = RetrofitClient.authApi.getAllOrders(creds = AuthRequest())
                if (response.isSuccessful) {
                    orders = response.body() ?: emptyList()
                }
            } finally {
                isLoading = false
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FF)).padding(20.dp)) {
        Text("Admin Panel", fontSize = 28.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins, color = Color(0xFFC1272D))

        // Summary Card
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFC1272D)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Total Revenue", color = Color.White, fontSize = 16.sp, fontFamily = Poppins)
                Text("Rs. ${orders.sumOf { it.totalAmount ?: 0.0 }}", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
                Text("Total Orders: ${orders.size}", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
            }
        }

        Text("Recent Orders", fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins, modifier = Modifier.padding(bottom = 10.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally), color = Color(0xFFC1272D))
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(orders.reversed()) { order ->
                    AdminOrderCard(order)
                }
            }
        }
    }
}

@Composable
fun AdminOrderCard(order: OrderResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("ID: ${order.id}", fontWeight = FontWeight.Bold, fontFamily = Poppins, color = Color.Gray)
                Text("Rs. ${order.totalAmount}", fontWeight = FontWeight.Bold, color = Color(0xFFC1272D))
            }
            Text("User: ${order.userEmail}", fontSize = 14.sp, color = Color.Black, modifier = Modifier.padding(vertical = 4.dp))
            Text("Items: ${order.itemsString}", fontSize = 12.sp, color = Color.Gray)
        }
    }
}
