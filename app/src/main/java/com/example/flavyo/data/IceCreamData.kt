package com.example.flavyo.data

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.flavyo.R
import com.google.gson.annotations.SerializedName

data class IceCreamData(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("mrp")
    val mrp: String? = null,
    @SerializedName("price")
    val price: String,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("image") // Changed from imageResName to image to match your sheet
    val image: String
)

@SuppressLint("DiscouragedApi")
@Composable
fun getDrawableId(name: String): Int {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(name, "drawable", context.packageName)
    return if (resourceId != 0) resourceId else R.drawable.launch_logo
}

fun getMockData() = listOf(
    IceCreamData("1", "Cotton Candy", "40", "35", "Sweet cotton candy flavor", "launch_logo"),
    IceCreamData("2", "Chocolate Cone", "40", "35", "Rich chocolate in a cone", "launch_logo"),
    IceCreamData("3", "Mango Kulfi", "40", "35", "Traditional mango kulfi", "launch_logo")
)