package com.example.flavyo.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun OrderSuccessScreen(
    totalAmount: Double,
    cartSummary: String,
    onGoHome: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userPreferences = remember { UserPreferences(context) }

    var isVisible by remember { mutableStateOf(false) }
    var isSavingToSheet by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isVisible = true
        scope.launch {
            try {
                val orderId = UUID.randomUUID().toString().substring(0, 8).uppercase()
                val userEmail = userPreferences.userEmail ?: "Guest"

                val orderRequest = OrderRequest(
                    id = orderId,
                    userEmail = userEmail,
                    timestamp = System.currentTimeMillis(),
                    totalAmount = totalAmount,
                    itemsString = cartSummary
                )

                // 1. Save the Order to the 'Orders' sheet
                val response = RetrofitClient.authApi.saveOrder(order = orderRequest)

                if (response.isSuccessful && response.body()?.status == "success") {

                    // 2. TRIGGER THE NOTIFICATION (This makes the bell work)
                    try {
                        RetrofitClient.authApi.sendNotification(
                            params = mapOf(
                                "target" to userEmail,
                                "title" to "Order Placed! 🍦",
                                "message" to "Your delicious order of $cartSummary has been received!"
                            )
                        )
                    } catch (e: Exception) {
                        Log.e("NotificationError", "Failed to send notification row")
                    }

                    isSavingToSheet = false
                    userPreferences.hasNewNotification = true
                }
            } catch (e: Exception) {
                Log.e("OrderError", "Failed: ${e.localizedMessage}")
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
                fontFamily = Poppins,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your Order Placed",
                fontSize = 24.sp,
                fontFamily = Poppins,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))
            if (isSavingToSheet) {
                Text("Syncing with ledger...", fontSize = 14.sp, color = Color.Gray, fontFamily = Poppins)
            } else {
                Text("Saved to History", fontSize = 14.sp, color = Color(0xFF4CAF50), fontFamily = Poppins)
            }

            Spacer(modifier = Modifier.height(40.dp))

            AnimatedVisibility(
                visible = isVisible,
                enter = scaleIn(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)) + fadeIn()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(240.dp)
                ) {
                    Bubble(size = 28.dp, Color(0xFFC1272D), Modifier.align(Alignment.TopStart).offset(x = 20.dp, y = 30.dp))
                    Bubble(size = 18.dp, Color(0xFFC1272D), Modifier.align(Alignment.CenterStart).offset(x = 10.dp, y = 60.dp))
                    Bubble(size = 14.dp, Color(0xFFC1272D), Modifier.align(Alignment.TopEnd).offset(x = (-20).dp, y = 50.dp))
                    Bubble(size = 8.dp, Color(0xFFC1272D), Modifier.align(Alignment.BottomEnd).offset(x = (-40).dp, y = (-20).dp))

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
        drawPath(path = path, color = Color.White, style = Stroke(width = 20f, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}