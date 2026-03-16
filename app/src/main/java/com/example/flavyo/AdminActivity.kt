package com.example.flavyo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.flavyo.data.UserPreferences
import com.example.flavyo.screens.*
import com.example.flavyo.ui.theme.FlavyoTheme

class AdminActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlavyoTheme {
                var currentScreen by remember { mutableStateOf("panel") }

                BackHandler(enabled = currentScreen != "panel") {
                    currentScreen = "panel"
                }

                when (currentScreen) {
                    "panel" -> AdminPanelScreen(
                        onPendingOrdersClick = { currentScreen = "pending_orders" },
                        onCompletedOrdersClick = { currentScreen = "completed_orders" },
                        onCancelOrdersClick = { currentScreen = "cancel_orders" },
                        onRevenueClick = { currentScreen = "revenue" },
                        onAddMenuClick = { currentScreen = "add_item" },
                        onAllItemMenuClick = { currentScreen = "all_items" },
                        onProfileClick = { currentScreen = "user_profiles" },
                        onCreateUserClick = { currentScreen = "create_user" },
                        onOrderHistoryClick = { currentScreen = "order_history" },
                        onLogoutClick = {
                            val userPreferences = UserPreferences(this)
                            userPreferences.logout()
                            val intent = Intent(this, RoleSelectionActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }
                    )
                    "pending_orders" -> PendingOrdersScreen { currentScreen = "panel" }
                    "completed_orders" -> CompletedOrdersScreen { currentScreen = "panel" }
                    "cancel_orders" -> AdminOrdersScreen("Cancel Orders") { currentScreen = "panel" }
                    "order_history" -> AdminOrdersScreen("Order History") { currentScreen = "panel" }
                    "all_items" -> AdminInventoryScreen { currentScreen = "panel" }
                    "add_item" -> AdminAddEditItemScreen { currentScreen = "panel" }
                    "user_profiles" -> AdminUsersScreen { currentScreen = "panel" }
                    "create_user" -> AdminCreateUserScreen { currentScreen = "panel" }
                    "revenue" -> AdminRevenueScreen { currentScreen = "panel" }
                }
            }
        }
    }
}
