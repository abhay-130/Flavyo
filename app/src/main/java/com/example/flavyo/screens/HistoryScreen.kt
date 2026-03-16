package com.example.flavyo.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.Order
import com.example.flavyo.ui.theme.Poppins
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryContent(
    orders: List<Order>,
    onOrderClick: (Order) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Order History",
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins
        )

        if (orders.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No orders placed yet", color = Color.Gray, fontFamily = Poppins)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(orders) { order ->
                    OrderItem(order = order, onClick = { onOrderClick(order) })
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: Order, onClick: () -> Unit) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    val dateString = dateFormat.format(Date(order.timestamp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF0F0F0))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Order #${order.id.take(8).uppercase()}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = Poppins
                )
                Text(
                    text = dateString,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontFamily = Poppins
                )
                Text(
                    text = "${order.items.values.sum()} items",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = Poppins
                )
            }
            Text(
                text = "Rs. ${order.totalAmount}",
                color = Color(0xFFC1272D),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = Poppins
            )
        }
    }
}
