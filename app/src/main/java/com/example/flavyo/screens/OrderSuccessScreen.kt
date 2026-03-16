package com.example.flavyo.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.OrderRequest
import com.example.flavyo.data.RetrofitClient
import com.example.flavyo.data.UserPreferences
import com.example.flavyo.ui.theme.Poppins
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun OrderSuccessScreen(
    totalAmount: Double,
    cartSummary: String, // Pass this from CartScreen (e.g., "2x Mango, 1x Choco")
    onGoHome: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userPreferences = remember { UserPreferences(context) }

    // Animation & Saving State
    var isVisible by remember { mutableStateOf(false) }
    var isSavingToSheet by remember { mutableStateOf(true) }

    // 1. TRIGGER ANIMATION AND API CALL ON START
    LaunchedEffect(Unit) {
        isVisible = true

        scope.launch {
            try {
                val orderRequest = OrderRequest(
                    id = UUID.randomUUID().toString().substring(0, 8).uppercase(),
                    userEmail = userPreferences.userEmail ?: "Guest",
                    timestamp = System.currentTimeMillis(),
                    totalAmount = totalAmount,
                    itemsString = cartSummary
                )

                val response = RetrofitClient.authApi.saveOrder(order = orderRequest)

                if (response.isSuccessful && response.body()?.status == "success") {
                    isSavingToSheet = false
                    Log.d("OrderSuccess", "Order recorded in Excel successfully")
                }
            } catch (e: Exception) {
                Log.e("OrderError", "Failed to save to sheet: ${e.localizedMessage}")
                isSavingToSheet = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(130.dp))

            Text(
                text = "Congrats",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins, // Use your branding font
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your Order Placed",
                fontSize = 24.sp,
                fontFamily = Poppins,
                color = Color.Black
            )

            // --- DATA SAVING STATUS ---
            Spacer(modifier = Modifier.height(12.dp))
            if (isSavingToSheet) {
                Text("Syncing with ledger...", fontSize = 14.sp, color = Color.Gray, fontFamily = Poppins)
            } else {
                Text("Saved to History", fontSize = 14.sp, color = Color(0xFF4CAF50), fontFamily = Poppins)
            }

            Spacer(modifier = Modifier.height(40.dp))

            // --- ANIMATED SUCCESS CIRCLE ---
            AnimatedVisibility(
                visible = isVisible,
                enter = scaleIn(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)) + fadeIn()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(240.dp)
                ) {
                    // Floating Bubbles
                    Bubble(size = 28.dp, Color(0xFFC1272D), Modifier.align(Alignment.TopStart).offset(x = 20.dp, y = 30.dp))
                    Bubble(size = 18.dp, Color(0xFFC1272D), Modifier.align(Alignment.CenterStart).offset(x = 10.dp, y = 60.dp))
                    Bubble(size = 14.dp, Color(0xFFC1272D), Modifier.align(Alignment.TopEnd).offset(x = (-20).dp, y = 50.dp))
                    Bubble(size = 8.dp, Color(0xFFC1272D), Modifier.align(Alignment.BottomEnd).offset(x = (-40).dp, y = (-20).dp))

                    // Main Red Circle
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFC1272D),
                        modifier = Modifier.size(160.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Bubble(size = 8.dp, Color.White, Modifier.align(Alignment.TopStart).offset(x = 35.dp, y = 35.dp))
                            Bubble(size = 4.dp, Color.White, Modifier.align(Alignment.BottomEnd).offset(x = (-40).dp, y = (-30).dp))
                            CustomTick(modifier = Modifier.size(60.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            Button(
                onClick = onGoHome,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC1272D)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Go Home", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
            }
        }
    }
}

@Composable
fun Bubble(size: androidx.compose.ui.unit.Dp, color: Color, modifier: Modifier) {
    Box(modifier = modifier.size(size).background(color, CircleShape))
}

@Composable
fun CustomTick(modifier: Modifier) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(width * 0.2f, height * 0.5f)
            lineTo(width * 0.45f, height * 0.75f)
            lineTo(width * 0.85f, height * 0.3f)
        }

        drawPath(
            path = path,
            color = Color.White,
            style = Stroke(
                width = 20f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}