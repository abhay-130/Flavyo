package com.example.flavyo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
    onUserClick: () -> Unit,
    onSalesExecutiveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.3f))
        
        Image(
            painter = painterResource(id = R.drawable.flavyo_logo_red),
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp)
        )
        
        Text(
            text = "Flavours that melt you!",
            color = Color(0xFFC1272D),
            fontSize = 20.sp,
            fontFamily = CoveredByYourGrace,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.weight(0.2f))
        
        Text(
            text = "Choose Your Role",
            fontSize = 18.sp,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        RoleCard(
            title = "Admin",
            description = "Manage orders and products",
            iconRes = R.drawable.flavyo_logo_red,
            onClick = onAdminClick
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        RoleCard(
            title = "Sales Executive",
            description = "Place orders for customers",
            iconRes = R.drawable.flavyo_logo_red,
            onClick = onSalesExecutiveClick
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        RoleCard(
            title = "User",
            description = "Order your favorite ice creams",
            iconRes = R.drawable.flavyo_logo_red,
            onClick = onUserClick
        )

        Spacer(modifier = Modifier.weight(0.5f))
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
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FF)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = description,
                    fontSize = 12.sp,
                    fontFamily = Poppins,
                    color = Color.Gray
                )
            }
        }
    }
}
