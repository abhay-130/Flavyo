package com.example.flavyo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.R
import com.example.flavyo.ui.theme.CoveredByYourGrace
import com.example.flavyo.ui.theme.Poppins

@Composable
fun AdminPanelScreen(
    onPendingOrdersClick: () -> Unit,
    onCompletedOrdersClick: () -> Unit,
    onCancelOrdersClick: () -> Unit,
    onRevenueClick: () -> Unit,
    onAddMenuClick: () -> Unit,
    onAllItemMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
    onCreateUserClick: () -> Unit,
    onOrderHistoryClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.flavyo_cream_logo_red),
                contentDescription = "Logo",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Flavours that melt you!",
                color = Color(0xFFC1272D),
                fontSize = 32.sp,
                letterSpacing = 2.sp,
                fontFamily = CoveredByYourGrace,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Header Row for Today's and Cancel Orders
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Today's",
                fontSize = 15.sp,
                color = Color.Black.copy(alpha = 0.7f),
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "Cancel Orders",
                modifier = Modifier.clickable { onCancelOrdersClick() },
                fontSize = 15.sp,
                color = Color.Gray.copy(alpha = 0.7f),
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium
            )
        }

        // Summary Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SummaryItem(
                    icon = Icons.Default.Info,
                    label = "Pending\nOrders",
                    value = "30",
                    iconColor = Color(0xFFC1272D),
                    textColor = Color.Red,
                    showBadge = true,
                    onClick = onPendingOrdersClick
                )
                SummaryItem(
                    icon = Icons.Default.CheckCircle,
                    label = "Completed\nOrders",
                    value = "10",
                    iconColor = Color(0xFF4CAF50),
                    textColor = Color(0xFF4CAF50),
                    onClick = onCompletedOrdersClick
                )
                SummaryItem(
                    icon = Icons.Default.CurrencyRupee,
                    label = "Total\nRevenue",
                    value = "Rs. 3500",
                    iconColor = Color.Black,
                    textColor = Color.Black,
                    onClick = onRevenueClick
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Grid Menu
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { AdminActionCard("Add Item", Icons.Default.AddCircleOutline, 40.dp, onAddMenuClick) }
            item { AdminActionCard("All Items", Icons.Default.Visibility, 40.dp, onAllItemMenuClick) }
            item { AdminActionCard("User Profiles", Icons.Default.AccountCircle, 40.dp, onProfileClick) }
            item { AdminActionCard("Create New User", Icons.Default.PersonAddAlt, 40.dp, onCreateUserClick) }
            item { AdminActionCard("History", Icons.Default.ShoppingBag, 40.dp, onOrderHistoryClick) }
            item { AdminActionCard("Log Out", Icons.Default.Logout, 40.dp, onLogoutClick) }
        }

        // Footer
        Text(
            text = "Design By\nAbhaY",
            color = Color(0xFFC1272D),
            fontSize = 13.sp,
            fontFamily = CoveredByYourGrace,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 16.dp),
            lineHeight = 13.sp
        )
    }
}

@Composable
fun SummaryItem(
    icon: ImageVector,
    label: String,
    value: String,
    iconColor: Color,
    textColor: Color,
    iconSize: androidx.compose.ui.unit.Dp = 28.dp,
    showBadge: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Box {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(iconSize)
            )
            if (showBadge) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(Color.Red, CircleShape)
                        .align(Alignment.TopEnd)
                )
            }
        }
        Text(
            text = label,
            fontSize = 11.sp,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 13.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            fontFamily = Poppins
        )
    }
}

@Composable
fun AdminActionCard(
    title: String,
    icon: ImageVector,
    iconSize: androidx.compose.ui.unit.Dp = 36.dp,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0ED))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFC1272D),
                modifier = Modifier.size(iconSize)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 15.sp,
                fontFamily = Poppins,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
