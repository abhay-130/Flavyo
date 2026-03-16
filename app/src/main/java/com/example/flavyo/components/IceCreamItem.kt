package com.example.flavyo.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.IceCreamData
import com.example.flavyo.data.getDrawableId

@Composable
fun IceCreamItem(
    data: IceCreamData,
    count: Int,
    onCountChange: (Int) -> Unit,
    onItemClick: () -> Unit = {},
    isRecent: Boolean = false
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 6.dp),
        border = BorderStroke(1.dp, Color(0xFFF0F0F0))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = getDrawableId(data.imageResName)),
                contentDescription = null,
                modifier = Modifier.size(70.dp).clip(RoundedCornerShape(12.dp)).clickable { onItemClick() }
            )

            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp).clickable { onItemClick() }) {
                Text(text = data.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "flavour", color = Color.LightGray, fontSize = 12.sp)
                Text(text = "Rs. ${data.price}", color = Color(0xFFC1272D), fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            if (isRecent) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFC1272D), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        // This is the key: it keeps the bottom of both texts on the same line
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "x",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp, // --- DECREASE THIS to control 'x' size ---
                            modifier = Modifier.padding(bottom = 0.dp) // Tiny nudge to keep it on the baseline
                        )
                        Text(
                            text = "$count",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp, // --- Keep number larger ---
                            modifier = Modifier.padding(bottom = 1.dp) // Adjust this to center the group vertically
                        )
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(110.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // MINUS
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color(0xFFFFEBEE), RoundedCornerShape(8.dp))
                                .clickable { if (count > 0) onCountChange(count - 1) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Remove, null, tint = Color.Red, modifier = Modifier.size(20.dp))
                        }

                        Text(text = "$count", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                        // PLUS
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color(0xFFC1272D), RoundedCornerShape(8.dp))
                                .clickable { onCountChange(count + 1) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Add, null, tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(22.dp).clickable { onCountChange(0) }
                    )
                }
            }
        }
    }
}
