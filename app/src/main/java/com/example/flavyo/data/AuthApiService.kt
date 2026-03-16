package com.example.flavyo.data

import retrofit2.Response
import retrofit2.http.*

interface AuthApiService {
    // Inventory endpoint (doGet)
    @GET("macros/s/AKfycbyt2JczE90fgImTqdJKmH1H9pEhiqKZ_Sb-N7f3E9mo0cV1dNaCs_Y5PJn_Y_X-RgHA-g/exec")
    suspend fun getInventory(): List<IceCreamData>

    // Signup endpoint (doPost)
    @POST("macros/s/AKfycbyt2JczE90fgImTqdJKmH1H9pEhiqKZ_Sb-N7f3E9mo0cV1dNaCs_Y5PJn_Y_X-RgHA-g/exec")
    suspend fun signUp(
        @Query("action") action: String = "signup",
        @Body user: AuthRequest
    ): Response<AuthResponse>

    // User Login endpoint (doPost)
    @POST("macros/s/AKfycbyt2JczE90fgImTqdJKmH1H9pEhiqKZ_Sb-N7f3E9mo0cV1dNaCs_Y5PJn_Y_X-RgHA-g/exec")
    suspend fun login(
        @Query("action") action: String = "login",
        @Body creds: AuthRequest
    ): Response<AuthResponse>

    // Profile Update endpoint (doPost)
    @POST("macros/s/AKfycbyt2JczE90fgImTqdJKmH1H9pEhiqKZ_Sb-N7f3E9mo0cV1dNaCs_Y5PJn_Y_X-RgHA-g/exec")
    suspend fun updateProfile(
        @Query("action") action: String = "update",
        @Body user: AuthRequest
    ): Response<AuthResponse>

    // Save Order endpoint (doPost)
    @POST("macros/s/AKfycbyt2JczE90fgImTqdJKmH1H9pEhiqKZ_Sb-N7f3E9mo0cV1dNaCs_Y5PJn_Y_X-RgHA-g/exec")
    suspend fun saveOrder(
        @Query("action") action: String = "saveOrder",
        @Body order: OrderRequest
    ): Response<AuthResponse>

    // Get Orders endpoint (doPost)
    @POST("macros/s/AKfycbyt2JczE90fgImTqdJKmH1H9pEhiqKZ_Sb-N7f3E9mo0cV1dNaCs_Y5PJn_Y_X-RgHA-g/exec")
    suspend fun getOrders(
        @Query("action") action: String = "getOrders",
        @Body request: AuthRequest // Sending userEmail inside AuthRequest
    ): Response<List<OrderResponse>>

    // Admin Login endpoint (doPost)
    @POST("macros/s/AKfycbyt2JczE90fgImTqdJKmH1H9pEhiqKZ_Sb-N7f3E9mo0cV1dNaCs_Y5PJn_Y_X-RgHA-g/exec")
    suspend fun adminLogin(
        @Query("action") action: String = "adminLogin",
        @Body creds: AuthRequest
    ): Response<AuthResponse>

    // Admin All Orders History endpoint (doPost)
    @POST("macros/s/AKfycbyt2JczE90fgImTqdJKmH1H9pEhiqKZ_Sb-N7f3E9mo0cV1dNaCs_Y5PJn_Y_X-RgHA-g/exec")
    suspend fun getAllOrders(
        @Query("action") action: String = "getAllOrders",
        @Body creds: AuthRequest // Using this as a dummy body to satisfy Retrofit
    ): Response<List<OrderResponse>>

    @POST("macros/s/AKfycbyt2JczE90fgImTqdJKmH1H9pEhiqKZ_Sb-N7f3E9mo0cV1dNaCs_Y5PJn_Y_X-RgHA-g/exec")
    suspend fun getFilteredOrders(
        @Query("action") action: String = "getFilteredOrders",
        @Body filter: Map<String, String> // e.g., {"statusFilter": "Pending"}
    ): Response<List<OrderResponse>>

    @POST("macros/s/AKfycbyt2JczE90fgImTqdJKmH1H9pEhiqKZ_Sb-N7f3E9mo0cV1dNaCs_Y5PJn_Y_X-RgHA-g/exec")
    suspend fun updateOrderStatus(
        @Query("action") action: String = "updateOrderStatus",
        @Body params: Map<String, String> // e.g., {"orderId": "123", "newStatus": "Accepted"}
    ): Response<AuthResponse>
}
