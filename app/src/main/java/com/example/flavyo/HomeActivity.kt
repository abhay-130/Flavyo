package com.example.flavyo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.flavyo.components.BottomNavigationBar
import com.example.flavyo.components.HeaderSection
import com.example.flavyo.data.IceCreamData
import com.example.flavyo.data.Order
import com.example.flavyo.data.RetrofitClient
import com.example.flavyo.data.UserPreferences
import com.example.flavyo.screens.*
import com.example.flavyo.ui.theme.FlavyoTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlavyoTheme {
                MainAppContainer()
            }
        }
    }
}

@Composable
fun MainAppContainer(initialScreen: String = "home") {
    val context = LocalContext.current
    var currentScreen by rememberSaveable { mutableStateOf(initialScreen) }
    var iceCreamList by remember { mutableStateOf<List<IceCreamData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var cartItems by rememberSaveable { mutableStateOf(mapOf<String, Int>()) }
    var selectedItem by remember { mutableStateOf<IceCreamData?>(null) }

    // --- State for Orders ---
    var orderHistory by rememberSaveable { mutableStateOf(listOf<Order>()) }
    var selectedOrder by remember { mutableStateOf<Order?>(null) }

    // --- State for Checkout/Payout ---
    var isPaying by remember { mutableStateOf(false) }
    var orderSuccess by remember { mutableStateOf(false) }
    
    // Captured order details for the success screen
    var capturedTotal by remember { mutableStateOf(0.0) }
    var capturedSummary by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.authApi.getInventory()
            iceCreamList = response
            isLoading = false
        } catch (e: Exception) {
            isLoading = false
            Log.e("FLAVYO_API", "Error: ${e.message}")
        }
    }

    // Handle back buttons
    if (selectedItem != null) { BackHandler { selectedItem = null } }
    if (isPaying) { BackHandler { isPaying = false } }
    if (orderSuccess) { BackHandler { orderSuccess = false } }
    if (selectedOrder != null) { BackHandler { selectedOrder = null } }

    Box(modifier = Modifier.fillMaxSize()) {
        // MAIN APP LAYER
        Scaffold(
            bottomBar = { 
                if (selectedItem == null && !isPaying && !orderSuccess && selectedOrder == null) {
                    BottomNavigationBar(currentScreen) { currentScreen = it }
                }
            },
            containerColor = Color(0xFFF8F9FF)
        ) { padding ->
            Column(modifier = Modifier.padding(padding)) {
                HeaderSection()
                Box(modifier = Modifier.weight(1f)) {
                    key(currentScreen) {
                        when (currentScreen) {
                            "home" -> HomeContent(
                                items = iceCreamList,
                                isLoading = isLoading,
                                cartItems = cartItems,
                                onUpdateCart = { id, count -> cartItems = if (count > 0) cartItems + (id to count) else cartItems - id },
                                onItemClick = { selectedItem = it }
                            )
                            "search" -> SearchContent(
                                iceCreamList = iceCreamList,
                                cartItems = cartItems,
                                onUpdateCart = { id, count -> cartItems = if (count > 0) cartItems + (id to count) else cartItems - id },
                                onItemClick = { selectedItem = it }
                            )
                            "cart" -> CartContent(
                                allIceCreams = iceCreamList,
                                cartItems = cartItems,
                                onUpdateCart = { id, count -> cartItems = if (count > 0) cartItems + (id to count) else cartItems - id },
                                onItemClick = { selectedItem = it },
                                onOrderSuccess = { isPaying = true }
                            )
                            "history" -> HistoryContent(
                                orders = orderHistory,
                                onOrderClick = { selectedOrder = it }
                            )
                            "profile" -> ProfileContent(
                                onLogout = {
                                    val userPreferences = UserPreferences(context)
                                    userPreferences.logout()
                                    val intent = Intent(context, RoleSelectionActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    context.startActivity(intent)
                                    (context as? android.app.Activity)?.finish()
                                }
                            )
                        }
                    }
                }
            }
        }

        // DETAIL OVERLAY
        if (selectedItem != null) {
            IceCreamDetailScreen(
                data = selectedItem!!,
                count = cartItems[selectedItem!!.id] ?: 0,
                onCountChange = { newCount ->
                    cartItems = if (newCount > 0) cartItems + (selectedItem!!.id to newCount) else cartItems - selectedItem!!.id
                },
                onBack = { selectedItem = null }
            )
        }

        // PAYOUT OVERLAY
        if (isPaying) {
            OrderDetailsScreen(
                allIceCreams = iceCreamList,
                cartItems = cartItems,
                onBack = { isPaying = false },
                onOrderPlaced = { total, summary ->
                    capturedTotal = total
                    capturedSummary = summary

                    val newOrder = Order(items = cartItems, totalAmount = capturedTotal.toInt())
                    orderHistory = listOf(newOrder) + orderHistory
                    
                    isPaying = false
                    orderSuccess = true
                }
            )
        }

        // --- ORDER SUCCESS OVERLAY ---
        if (orderSuccess) {
            OrderSuccessScreen(
                totalAmount = capturedTotal,
                cartSummary = capturedSummary,
                onGoHome = {
                    orderSuccess = false
                    cartItems = emptyMap() // Clear the cart after success
                    currentScreen = "home"
                }
            )
        }

        // ORDER HISTORY DETAIL OVERLAY
        if (selectedOrder != null) {
            OrderHistoryDetailScreen(
                order = selectedOrder!!,
                allIceCreams = iceCreamList,
                onBack = { selectedOrder = null }
            )
        }
    }
}
