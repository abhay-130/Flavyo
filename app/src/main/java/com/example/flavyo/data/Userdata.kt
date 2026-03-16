package com.example.flavyo.data

import com.google.gson.annotations.SerializedName

data class Userdata(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("address")
    val address: String? = null
)