package com.example.flavyo.data

import retrofit2.Response
import retrofit2.http.*

interface AuthApiService {
    // Inventory endpoint (doGet)
    @GET("macros/s/AKfycbwDnFggPHc-8dCasjBe4LCttvwjEu5P0_rNkEapJ0jT1bcTxafgZYX1TCDURRuKHqUv/exec")
    suspend fun getInventory(): List<IceCreamData>

    // Signup endpoint (doPost)
    @POST("macros/s/AKfycbwDnFggPHc-8dCasjBe4LCttvwjEu5P0_rNkEapJ0jT1bcTxafgZYX1TCDURRuKHqUv/exec")
    suspend fun signUp(
        @Query("action") action: String = "signup",
        @Body user: AuthRequest
    ): Response<AuthResponse>

    // User Login endpoint (doPost)
    @POST("macros/s/AKfycbwDnFggPHc-8dCasjBe4LCttvwjEu5P0_rNkEapJ0jT1bcTxafgZYX1TCDURRuKHqUv/exec")
    suspend fun login(
        @Query("action") action: String = "login",
        @Body creds: AuthRequest
    ): Response<AuthResponse>

    // Profile Update endpoint (doPost)
    @POST("macros/s/AKfycbwDnFggPHc-8dCasjBe4LCttvwjEu5P0_rNkEapJ0jT1bcTxafgZYX1TCDURRuKHqUv/exec")
    suspend fun updateProfile(
        @Query("action") action: String = "update",
        @Body user: AuthRequest
    ): Response<AuthResponse>

    // Save Order endpoint (doPost)
    @POST("macros/s/AKfycbwDnFggPHc-8dCasjBe4LCttvwjEu5P0_rNkEapJ0jT1bcTxafgZYX1TCDURRuKHqUv/exec")
    suspend fun saveOrder(
        @Query("action") action: String = "saveOrder",
        @Body order: OrderRequest
    ): Response<AuthResponse>

    // Get Orders endpoint (doPost)
    @POST("macros/s/AKfycbwDnFggPHc-8dCasjBe4LCttvwjEu5P0_rNkEapJ0jT1bcTxafgZYX1TCDURRuKHqUv/exec")
    suspend fun getOrders(
        @Query("action") action: String = "getOrders",
        @Body params: Map<String, String> // Passing the email here
    ): Response<List<OrderResponse>>

    // Admin Login endpoint (doPost)
    @POST("macros/s/AKfycbwDnFggPHc-8dCasjBe4LCttvwjEu5P0_rNkEapJ0jT1bcTxafgZYX1TCDURRuKHqUv/exec")
    suspend fun adminLogin(
        @Query("action") action: String = "adminLogin",
        @Body creds: AuthRequest
    ): Response<AuthResponse>

    // Admin All Orders History endpoint (doPost)
    @POST("macros/s/AKfycbwDnFggPHc-8dCasjBe4LCttvwjEu5P0_rNkEapJ0jT1bcTxafgZYX1TCDURRuKHqUv/exec")
    suspend fun getAllOrders(
        @Query("action") action: String = "getAllOrders",
        @Body creds: AuthRequest // Using this as a dummy body to satisfy Retrofit
    ): Response<List<OrderResponse>>

    @POST("macros/s/AKfycbwDnFggPHc-8dCasjBe4LCttvwjEu5P0_rNkEapJ0jT1bcTxafgZYX1TCDURRuKHqUv/exec")
    suspend fun getFilteredOrders(
        @Query("action") action: String = "getFilteredOrders",
        @Body filter: Map<String, String> // e.g., {"statusFilter": "Pending"}
    ): Response<List<OrderResponse>>

    @POST("macros/s/AKfycbwDnFggPHc-8dCasjBe4LCttvwjEu5P0_rNkEapJ0jT1bcTxafgZYX1TCDURRuKHqUv/exec")
    suspend fun updateOrderStatus(
        @Query("action") action: String = "updateOrderStatus",
        @Body params: Map<String, String> // e.g., {"orderId": "123", "newStatus": "Accepted"}
    ): Response<AuthResponse>

    // Live Notification
    @POST("macros/s/AKfycbwDnFggPHc-8dCasjBe4LCttvwjEu5P0_rNkEapJ0jT1bcTxafgZYX1TCDURRuKHqUv/exec")
    suspend fun getNotifications(
        @Query("action") action: String = "getNotifications",
        @Body params: Map<String, String>
    ): Response<List<NotificationItem>>

    @POST("macros/s/AKfycbwDnFggPHc-8dCasjBe4LCttvwjEu5P0_rNkEapJ0jT1bcTxafgZYX1TCDURRuKHqUv/exec")
    suspend fun sendNotification(
        @Query("action") action: String = "sendNotification",
        @Body params: Map<String, String>
    ): Response<AuthResponse>

    // Updated: Get All Users (for Admin)
    @POST("macros/s/AKfycbwDnFggPHc-8dCasjBe4LCttvwjEu5P0_rNkEapJ0jT1bcTxafgZYX1TCDURRuKHqUv/exec")
    suspend fun getAllUsers(
        @Query("action") action: String = "getAllUsers",
        // We send an empty body because doPost requires it in your script setup
        @Body body: Map<String, String> = emptyMap()
    ): Response<List<Userdata>>

    // Inventory Items endpoint (doPost)
    @POST("macros/s/AKfycbwDnFggPHc-8dCasjBe4LCttvwjEu5P0_rNkEapJ0jT1bcTxafgZYX1TCDURRuKHqUv/exec")
    suspend fun addItem(
        @Query("action") action: String = "addItem",
        @Body itemData: Map<String, String>
    ): Response<AuthResponse>
}
