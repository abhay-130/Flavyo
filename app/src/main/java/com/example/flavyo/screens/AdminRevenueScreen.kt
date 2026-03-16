package com.example.flavyo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.RetrofitClient
import com.example.flavyo.data.AuthRequest
import com.example.flavyo.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminRevenueScreen(onBack: () -> Unit) {
    var totalRevenue by remember { mutableStateOf(0.0) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.authApi.getAllOrders(creds = AuthRequest())
            if (response.isSuccessful) {
                // Calculate revenue from Accepted orders
                totalRevenue = response.body()?.filter { it.status == "Accepted" }?.sumOf { it.totalAmount ?: 0.0 } ?: 0.0
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
                title = { Text("Revenue Details", fontFamily = Poppins, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color(0xFFC1272D))
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0ED))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = Color(0xFFC1272D),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Total Revenue",
                            fontFamily = Poppins,
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "Rs. ${totalRevenue.toInt()}",
                            fontFamily = Poppins,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFC1272D)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "Summary based on Accepted orders.",
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
