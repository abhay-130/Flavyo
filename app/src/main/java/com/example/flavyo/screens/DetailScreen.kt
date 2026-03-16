package com.example.flavyo.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.IceCreamData
import com.example.flavyo.data.getDrawableId

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
            .background(Color(0xFFF8F9FF)) // Match app background
            .verticalScroll(scrollState)
            .padding(top = 50.dp)
    ) {
        // --- 1. Top Bar ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Yellow circular back button from screenshot
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

            Text(
                text = data.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.weight(1.3f))
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
                painter = painterResource(id = getDrawableId(data.imageResName)),
                contentDescription = data.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            )
        }

        // --- 3. Description & Ingredients ---
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = "Short description", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Experience the rich and creamy texture of our ${data.name}. Crafted with the finest ingredients to ensure every scoop is a delight.",
                color = Color.Gray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Ingredients", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            // Bullets
            val ingredients = listOf("Flavour Pan", "Cream", "Milk")
            ingredients.forEach { ingredient ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Box(modifier = Modifier.size(6.dp).background(Color.Black, CircleShape))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = ingredient, color = Color.Gray, fontSize = 14.sp)
                }
            }
        }

        // --- 4. Red Cart Controller (Sticky-style at bottom) ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFC1272D))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Add To Chart",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Minus Button
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
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    // Plus Button
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

                    // Delete/Reset Button
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

        // --- 5. Back to Menu Button ---
        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, Color(0xFFC1272D)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Back to Menu", color = Color(0xFFC1272D), fontWeight = FontWeight.Bold)
        }
    }
}
