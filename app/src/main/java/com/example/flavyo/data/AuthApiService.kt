package com.example.flavyo.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @GET("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun getInventory(): List<IceCreamData>

    @POST("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun signUp(@Query("action") action: String = "signup", @Body user: AuthRequest): Response<AuthResponse>

    @POST("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun login(@Query("action") action: String = "login", @Body creds: AuthRequest): Response<AuthResponse>

    @POST("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun saveOrder(@Query("action") action: String = "saveOrder", @Body order: Map<String, @JvmSuppressWildcards Any?>): Response<AuthResponse>

    @POST("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun getOrders(@Query("action") action: String = "getOrders", @Body params: Map<String, String>): Response<List<OrderResponse>>

    @POST("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun getAllOrders(@Query("action") action: String = "getAllOrders", @Body creds: AuthRequest): Response<List<OrderResponse>>

    @POST("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun getFilteredOrders(@Query("action") action: String = "getFilteredOrders", @Body filter: Map<String, String>): Response<List<OrderResponse>>

    @POST("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun updateOrderStatus(@Query("action") action: String = "updateOrderStatus", @Body params: Map<String, String>): Response<AuthResponse>

    @POST("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun getNotifications(@Query("action") action: String = "getNotifications", @Body params: Map<String, String>): Response<List<NotificationItem>>

    @POST("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun sendNotification(@Query("action") action: String = "sendNotification", @Body params: Map<String, String>): Response<AuthResponse>

    @POST("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun addItem(@Query("action") action: String = "addItem", @Body itemData: Map<String, String>): Response<AuthResponse>

    @POST("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun getAllUsers(@Query("action") action: String = "getUsers", @Body body: Map<String, String>): Response<List<Userdata>>

    @POST("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun adminLogin(@Query("action") action: String = "adminLogin", @Body creds: AuthRequest): Response<AuthResponse>

    @POST("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun updateProfile(@Query("action") action: String = "update", @Body user: AuthRequest): Response<AuthResponse>

    @POST("macros/s/AKfycbzgBsIeliAZlZNfjQ43df_aKDVp0rgW9_gwiULF5oocaSrKuAY_cPt4tjCO00aTsgzpIA/exec")
    suspend fun deleteOrder(@Query("action") action: String = "deleteOrder", @Body params: Map<String, String>): Response<AuthResponse>
}
