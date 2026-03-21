package com.example.flavyo.data

import com.google.gson.annotations.SerializedName

// The data we send to the sheet
data class AuthRequest(
    val name: String? = null,
    val emailOrPhone: String? = null,
    val address: String? = null,
    val password: String? = null,
    val userEmail: String? = null
)

data class AuthResponse(
    val status: String? = null,
    val result: String? = null,
    val message: String? = null,
    val name: String? = null,
    val email: String? = null,
    val address: String? = null,
    val user: Userdata? = null,
)

// Data class for Order requests/responses matching the Apps Script JSON keys
data class OrderRequest(
    @SerializedName("customerName") val customerName: String?,
    @SerializedName("userEmail") val userEmail: String?,
    @SerializedName("timestamp") val timestamp: String?,
    @SerializedName("totalAmount") val totalAmount: Double?,
    @SerializedName("itemsString") val itemsString: String?,
    @SerializedName("status") val status: String,
    @SerializedName("id") val id: String?
)

data class OrderResponse(
    @SerializedName("customerName") val customerName: String? = null,
    @SerializedName("userEmail") val userEmail: String? = null,
    @SerializedName("timestamp") val timestamp: String? = null,
    @SerializedName("totalAmount") val totalAmount: Double? = null,
    @SerializedName("itemsString") val itemsString: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("id") val id: String? = null
)
