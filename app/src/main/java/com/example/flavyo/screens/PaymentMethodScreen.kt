package com.example.flavyo.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.ui.theme.Poppins

@Composable
fun PaymentMethodScreen(
    totalAmount: Double,
    onBack: () -> Unit,
    onPaymentMethodSelected: (String) -> Unit
) {
    var selectedMethod by remember { mutableStateOf("Google Pay") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
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
                text = "Payment Method",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Select how you want to pay",
            fontSize = 16.sp,
            color = Color.Gray,
            fontFamily = Poppins,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        PaymentOption(
            title = "Google Pay",
            icon = Icons.Default.QrCode,
            isSelected = selectedMethod == "Google Pay",
            onClick = { selectedMethod = "Google Pay" }
        )

        PaymentOption(
            title = "Cash on Delivery",
            icon = Icons.Default.Payments,
            isSelected = selectedMethod == "Cash on Delivery",
            onClick = { selectedMethod = "Cash on Delivery" }
        )

        Spacer(modifier = Modifier.height(40.dp))

        // --- Summary Card ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FF))
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Amount to Pay", fontFamily = Poppins, fontWeight = FontWeight.Medium)
                Text("Rs. ${totalAmount.toInt()}", color = Color(0xFFC1272D), fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // --- Action Button ---
        Button(
            onClick = { onPaymentMethodSelected(selectedMethod) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC1272D)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                "Confirm & Proceed",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins
            )
        }
    }
}

@Composable
fun PaymentOption(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFFF4F4) else Color.White
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) Color(0xFFC1272D) else Color(0xFFEEEEEE)
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(if (isSelected) Color(0xFFC1272D) else Color(0xFFF5F5F5), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) Color.White else Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontFamily = Poppins,
                modifier = Modifier.weight(1f)
            )

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFFC1272D)
                )
            }
        }
    }
}
