package com.example.flavyo.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
            // Updated: Use 'image' field from data (assuming it refers to a drawable name)
            Image(
                painter = painterResource(id = getDrawableId(data.image)),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp) // Slightly larger to fit new info
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onItemClick() }
            )

            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp).clickable { onItemClick() }) {
                Text(text = data.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = Poppins)

                // Delete description text from here (it stays in DetailScreen)
                Spacer(modifier = Modifier.height(4.dp))

                // 2. Vertical Price Stack
                Column {
                    if (data.mrp?.isNotEmpty() == true && data.mrp != data.price) {
                        Text(
                            text = "Rs. ${data.mrp}",
                            style = TextStyle(
                                textDecoration = TextDecoration.LineThrough,
                                fontFamily = Poppins
                            ),
                            color = Color.LightGray,
                            fontSize = 13.sp
                        )
                    }
                    Text(
                        text = "Rs. ${data.price}",
                        color = Color(0xFFC1272D),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        fontFamily = Poppins
                    )
                }
            }

            if (isRecent) {
                RecentBadge(count)
            } else {
                CounterSection(count, onCountChange)
            }
        }
    }
}

@Composable
fun RecentBadge(count: Int) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(Color(0xFFC1272D), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "x", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
            Text(text = "$count", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

@Composable
fun CounterSection(count: Int, onCountChange: (Int) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(110.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            // MINUS
            IconButtonBox(icon = Icons.Default.Remove, bgColor = Color(0xFFFFEBEE), iconColor = Color.Red) {
                if (count > 0) onCountChange(count - 1)
            }

            Text(text = "$count", fontWeight = FontWeight.Bold, fontSize = 14.sp)

            // PLUS
            IconButtonBox(icon = Icons.Default.Add, bgColor = Color(0xFFC1272D), iconColor = Color.White) {
                onCountChange(count + 1)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp).clickable { onCountChange(0) }
        )
    }
}

@Composable
fun IconButtonBox(icon: androidx.compose.ui.graphics.vector.ImageVector, bgColor: Color, iconColor: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(bgColor, RoundedCornerShape(8.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, null, tint = iconColor, modifier = Modifier.size(18.dp))
    }
}