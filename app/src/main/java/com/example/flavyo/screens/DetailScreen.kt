package com.example.flavyo.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.IceCreamData
import com.example.flavyo.data.getDrawableId
import com.example.flavyo.ui.theme.Poppins

@Composable
fun IceCreamDetailScreen(
    data: IceCreamData,
    count: Int,
    onCountChange: (Int) -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FF))
            .verticalScroll(scrollState)
            .padding(top = 5.dp)
    ) {
        // --- 1. Top Bar (With Yellow Back Button & Price Stack) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFFFEEBC), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFFC69C2C)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Stack MRP above Selling Price
            Column(horizontalAlignment = Alignment.End) {
                if (!data.mrp.isNullOrEmpty() && data.mrp != data.price) {
                    Text(
                        text = "MRP Rs. ${data.mrp}",
                        style = TextStyle(textDecoration = TextDecoration.LineThrough),
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        fontFamily = Poppins
                    )
                }
                Text(
                    text = "Rs. ${data.price}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC1272D),
                    fontFamily = Poppins
                )
            }
        }

        // --- 2. Main Product Image ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(32.dp),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Image(
                painter = painterResource(id = getDrawableId(data.image)),
                contentDescription = data.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }

        // --- 3. Description Section ---
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = data.name,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Description",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = data.description ?: "Indulge in the premium taste of our ${data.name}. Handcrafted for the ultimate dessert experience.",
                color = Color.Gray,
                fontSize = 14.sp,
                lineHeight = 22.sp,
                fontFamily = Poppins
            )
        }

        // --- 4. Add To Cart Controller (Sticky Card) ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFC1272D))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Add To Cart",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = Poppins,
                    modifier = Modifier.weight(1f)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Minus
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                            .clickable { if (count > 0) onCountChange(count - 1) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Remove, null, tint = Color.White)
                    }

                    Text(
                        text = "$count",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = Poppins,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    // Plus
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                            .clickable { onCountChange(count + 1) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, null, tint = Color.White)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Reset
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Reset",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onCountChange(0) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- 5. Back to Menu Button ---
        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 60.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, Color(0xFFC1272D)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Back to Menu",
                color = Color(0xFFC1272D),
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins
            )
        }
    }
}