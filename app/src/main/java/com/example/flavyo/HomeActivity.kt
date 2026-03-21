package com.example.flavyo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.flavyo.components.BottomNavigationBar
import com.example.flavyo.data.IceCreamData
import com.example.flavyo.data.NotificationItem
import com.example.flavyo.data.Order
import com.example.flavyo.data.OrderResponse
import com.example.flavyo.data.RetrofitClient
import com.example.flavyo.data.UserPreferences
import com.example.flavyo.screens.CartScreen
import com.example.flavyo.screens.HistoryContent
import com.example.flavyo.screens.HomeScreen
import com.example.flavyo.screens.IceCreamDetailScreen
import com.example.flavyo.screens.OrderDetailsScreen
import com.example.flavyo.screens.OrderHistoryDetailScreen
import com.example.flavyo.screens.OrderSuccessScreen
import com.example.flavyo.screens.PaymentMethodScreen
import com.example.flavyo.screens.PaymentProcessingScreen
import com.example.flavyo.screens.ProfileScreen
import com.example.flavyo.screens.SearchScreen
import com.example.flavyo.screens.UserNotificationScreen
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
fun MainAppContainer() {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    // Remember the scroll
    val homeScrollState = rememberLazyListState() // Add this for Home position

    // Check if user is logged in
    if (userPreferences.userEmail == null) {
        // Not logged in, redirect to LoginActivity
        context.startActivity(Intent(context, LoginActivity::class.java))
        (context as? ComponentActivity)?.finish()
        return
    }

    var currentScreen by rememberSaveable { mutableStateOf("Home") }

    // --- State for Inventory ---
    var iceCreamList by remember { mutableStateOf<List<IceCreamData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val data = RetrofitClient.authApi.getInventory()
            iceCreamList = data
            isLoading = false
        } catch (e: Exception) {
            Log.e("HomeActivity", "Error fetching inventory", e)
            isLoading = false
        }
    }

    // --- State for Cart ---
    var cartItems by remember { mutableStateOf<List<IceCreamData>>(emptyList()) }

    // --- State for Checkout/Payout ---
    var isConfirmingOrder by remember { mutableStateOf(false) }
    var isSelectingPaymentMethod by remember { mutableStateOf(false) }
    var isPaying by remember { mutableStateOf(false) }
    var orderSuccess by remember { mutableStateOf(false) }

    // Captured order details
    var capturedTotal by remember { mutableDoubleStateOf(0.0) }
    var capturedSummary by remember { mutableStateOf("") }
    var selectedPaymentMethod by remember { mutableStateOf("") }

    // --- Notification State ---
    val userEmail = userPreferences.userEmail
    var notificationList by remember { mutableStateOf<List<NotificationItem>>(emptyList()) }
    var hasNotification by remember { mutableStateOf(false) }

    // --- Live Notification Heartbeat ---
    LaunchedEffect(Unit) {
        while(true) { // This keeps checking every 30 seconds
            try {
                val response = RetrofitClient.authApi.getNotifications(params = mapOf("userEmail" to userEmail!!))
                if (response.isSuccessful) {
                    val newNotifications = response.body() ?: emptyList()

                    // Logic: If server has more notifications than our last seen count, show dot
                    if (newNotifications.size > userPreferences.lastKnownNotificationCount) {
                        hasNotification = true
                    }
                    notificationList = newNotifications
                }
            } catch (e: Exception) {
                Log.e("HomeActivity", "Notification Sync Error", e)
            }
            kotlinx.coroutines.delay(3000) // Wait 3 seconds before checking again
        }
    }

    // --- State for Orders History ---
    var userOrders by remember { mutableStateOf<List<OrderResponse>>(emptyList()) }
    var selectedOrder by remember { mutableStateOf<Order?>(null) }
    var selectedIceCream by remember { mutableStateOf<IceCreamData?>(null) }

    LaunchedEffect(currentScreen) {
        if (currentScreen == "History") {
            try {
                // Ensure you are passing the current logged-in user's email
                val response = RetrofitClient.authApi.getOrders(
                    params = mapOf("userEmail" to userEmail!!)
                )
                if (response.isSuccessful) {
                    userOrders = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                Log.e("HomeActivity", "Error fetching orders", e)
            }
        }
    }

    // Handle back press
    BackHandler(enabled = currentScreen != "Home" || selectedOrder != null || selectedIceCream != null || isConfirmingOrder || isSelectingPaymentMethod || isPaying || orderSuccess) {
        if (orderSuccess) {
            orderSuccess = false
            currentScreen = "Home"
        } else if (isPaying) {
            isPaying = false
            isSelectingPaymentMethod = true
        } else if (isSelectingPaymentMethod) {
            isSelectingPaymentMethod = false
            isConfirmingOrder = true
        } else if (isConfirmingOrder) {
            isConfirmingOrder = false
        } else if (selectedOrder != null) {
            selectedOrder = null
        } else if (selectedIceCream != null) {
            selectedIceCream = null
        } else {
            currentScreen = "Home"
        }
    }

    Scaffold(
        bottomBar = {
            if (!isConfirmingOrder && !isSelectingPaymentMethod && !isPaying && !orderSuccess && currentScreen != "Notifications" && selectedOrder == null && selectedIceCream == null) {
                BottomNavigationBar(
                    currentScreen = currentScreen,
                    onScreenSelected = { currentScreen = it },
                    hasNotification = hasNotification
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            val cartMap = cartItems.groupBy { it.id }.mapValues { it.value.size }

            if (selectedOrder != null) {
                OrderHistoryDetailScreen(
                    order = selectedOrder!!,
                    allIceCreams = iceCreamList,
                    onBack = { selectedOrder = null }
                )
            } else if (selectedIceCream != null) {
                IceCreamDetailScreen(
                    data = selectedIceCream!!,
                    count = cartMap[selectedIceCream!!.id] ?: 0,
                    onCountChange = { newCount ->
                        val item = selectedIceCream!!
                        val newList = cartItems.toMutableList()
                        val currentCount = newList.count { it.id == item.id }
                        if (newCount > currentCount) {
                            repeat(newCount - currentCount) { newList.add(item) }
                        } else if (newCount < currentCount) {
                            repeat(currentCount - newCount) {
                                val index = newList.indexOfLast { it.id == item.id }
                                if (index != -1) newList.removeAt(index)
                            }
                        }
                        cartItems = newList
                    },
                    onBack = { selectedIceCream = null }
                )
            } else {
                when (currentScreen) {
                    "Home" -> {
                        HomeScreen(
                            iceCreamList = iceCreamList,
                            cartItems = cartMap,
                            isLoading = isLoading,
                            scrollState = homeScrollState,
                            onUpdateCart = { id, newCount ->
                                val item = iceCreamList.find { it.id == id }
                                if (item != null) {
                                    val newList = cartItems.toMutableList()
                                    val currentCount = newList.count { it.id == id }
                                    if (newCount > currentCount) {
                                        repeat(newCount - currentCount) { newList.add(item) }
                                    } else if (newCount < currentCount) {
                                        repeat(currentCount - newCount) {
                                            val index = newList.indexOfLast { it.id == id }
                                            if (index != -1) newList.removeAt(index)
                                        }
                                    }
                                    cartItems = newList
                                }
                            },
                            onItemClick = { selectedIceCream = it },
                            onNotificationClick = {
                                currentScreen = "Notifications"
                                hasNotification = false // Hide the dot locally
                                // Update the preference so the dot doesn't come back for old notifications
                                userPreferences.lastKnownNotificationCount = notificationList.size
                            },
                            hasNotification = hasNotification
                        )
                    }
                    "Search" -> {
                        SearchScreen(
                            iceCreamList = iceCreamList,
                            cartItems = cartMap,
                            onUpdateCart = { id, newCount ->
                                val item = iceCreamList.find { it.id == id }
                                if (item != null) {
                                    val newList = cartItems.toMutableList()
                                    val currentCount = newList.count { it.id == id }
                                    if (newCount > currentCount) {
                                        repeat(newCount - currentCount) { newList.add(item) }
                                    } else if (newCount < currentCount) {
                                        repeat(currentCount - newCount) {
                                            val index = newList.indexOfLast { it.id == id }
                                            if (index != -1) newList.removeAt(index)
                                        }
                                    }
                                    cartItems = newList
                                }
                            },
                            onItemClick = { selectedIceCream = it }
                        )
                    }
                    "Cart" -> {
                        CartScreen(
                            cartItems = cartItems,
                            onUpdateCart = { id, newCount ->
                                val item = iceCreamList.find { it.id == id }
                                if (item != null) {
                                    val newList = cartItems.toMutableList()
                                    val currentCount = newList.count { it.id == id }
                                    if (newCount > currentCount) {
                                        repeat(newCount - currentCount) { newList.add(item) }
                                    } else if (newCount < currentCount) {
                                        repeat(currentCount - newCount) {
                                            val index = newList.indexOfLast { it.id == id }
                                            if (index != -1) newList.removeAt(index)
                                        }
                                    }
                                    cartItems = newList
                                }
                            },
                            onCheckout = { total, summary ->
                                capturedTotal = total
                                capturedSummary = summary
                                isConfirmingOrder = true // Flow: Cart -> Order Details
                            },
                            onItemClick = { selectedIceCream = it }
                        )
                    }
                    "History" -> {
                        val ordersList = userOrders.map { resp ->
                            val itemsMap = try {
                                resp.itemsString?.split(",")?.associate {
                                    val parts = it.trim().split("x ")
                                    val qty = parts[0].toInt()
                                    val name = parts[1]
                                    val iceCream = iceCreamList.find { ic -> ic.name == name }
                                    (iceCream?.id ?: name) to qty
                                } ?: emptyMap()
                            } catch (e: Exception) {
                                emptyMap()
                            }

                            Order(
                                id = resp.id ?: "",
                                items = itemsMap,
                                timestamp = resp.timestamp?.toLongOrNull() ?: 0L,
                                totalAmount = resp.totalAmount?.toInt() ?: 0
                            )
                        }

                        HistoryContent(
                            orders = ordersList,
                            onOrderClick = { selectedOrder = it }
                        )
                    }
                    "Profile" -> ProfileScreen(
                        onLogout = {
                            userPreferences.userEmail = null
                            userPreferences.lastKnownNotificationCount = 0
                            context.startActivity(Intent(context, RoleSelectionActivity::class.java))
                            (context as? ComponentActivity)?.finish()
                        }
                    )
                    "Notifications" -> {
                        UserNotificationScreen(
                            notifications = notificationList, // Pass the live fetched list
                            onBack = { currentScreen = "Home" }
                        )
                    }                }
            }

            if (isConfirmingOrder) {
                OrderDetailsScreen(
                    allIceCreams = iceCreamList,
                    cartItems = cartMap,
                    onBack = { isConfirmingOrder = false },
                    onProceedToPayment = { total, summary ->
                        capturedTotal = total
                        capturedSummary = summary
                        isConfirmingOrder = false
                        isSelectingPaymentMethod = true // Flow: Order Details -> Payment Method
                    }
                )
            }

            if (isSelectingPaymentMethod) {
                PaymentMethodScreen(
                    totalAmount = capturedTotal,
                    onBack = {
                        isSelectingPaymentMethod = false
                        isConfirmingOrder = true
                    },
                    onPaymentMethodSelected = { method ->
                        selectedPaymentMethod = method
                        isSelectingPaymentMethod = false
                        isPaying = true // Flow: Payment Method -> Payment Processing
                    }
                )
            }

            if (isPaying) {
                PaymentProcessingScreen(
                    totalAmount = capturedTotal,
                    orderSummary = capturedSummary,
                    onPaymentSuccess = {
                        isPaying = false
                        orderSuccess = true // Flow: Payment -> Success Screen
                        cartItems = emptyList() // Clear cart here
                    },
                    onCancel = {
                        isPaying = false
                        isSelectingPaymentMethod = true
                    }
                )
            }

            if (orderSuccess) {
                OrderSuccessScreen(
                    totalAmount = capturedTotal,
                    cartSummary = capturedSummary,
                    onGoHome = {
                        orderSuccess = false
                        currentScreen = "Home"
                    }
                )
            }
        }
    }
}
