package com.example.flavyo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.ui.theme.Poppins
import kotlinx.coroutines.delay

@Composable
fun PaymentProcessingScreen(
    totalAmount: Double,
    orderSummary: String,
    onPaymentSuccess: () -> Unit,
    onCancel: () -> Unit
) {
    // Simulate a network delay for payment processing
    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds delay
        onPaymentSuccess()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)), // Solid light grey background to hide everything behind
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Processing Payment",
                    fontFamily = Poppins,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                CircularProgressIndicator(
                    color = Color(0xFFC1272D),
                    strokeWidth = 4.dp,
                    modifier = Modifier.size(50.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Amount: Rs. $totalAmount",
                    fontFamily = Poppins,
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Text(
                    text = "Please do not press back or close the app",
                    fontFamily = Poppins,
                    fontSize = 12.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextButton(onClick = onCancel) {
                    Text(
                        "Cancel Transaction",
                        color = Color(0xFFC1272D),
                        fontFamily = Poppins
                    )
                }
            }
        }
    }
}
