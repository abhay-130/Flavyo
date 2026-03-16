package com.example.flavyo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.R
import com.example.flavyo.ui.theme.CoveredByYourGrace
import com.example.flavyo.ui.theme.Poppins

@Composable
fun RoleSelectionScreen(
    onAdminClick: () -> Unit,
    onUserClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        
        Image(
            painter = painterResource(id = R.drawable.flavyo_logo_red),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
        
        Text(
            text = "Flavours that melt you!",
            color = Color(0xFFC1272D),
            fontSize = 24.sp,
            fontFamily = CoveredByYourGrace,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(60.dp))
        
        Text(
            text = "Choose Your Role",
            fontSize = 22.sp,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        RoleCard(
            title = "Admin",
            description = "Manage orders and products",
            iconRes = R.drawable.flavyo_logo_red, // Replace with appropriate icon if available
            onClick = onAdminClick
        )
        
        Spacer(modifier = Modifier.height(20.dp))
        
        RoleCard(
            title = "User",
            description = "Order your favorite ice creams",
            iconRes = R.drawable.flavyo_logo_red, // Replace with appropriate icon if available
            onClick = onUserClick
        )
    }
}

@Composable
fun RoleCard(
    title: String,
    description: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FF)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            
            Spacer(modifier = Modifier.width(20.dp))
            
            Column {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    fontFamily = Poppins,
                    color = Color.Gray
                )
            }
        }
    }
}
