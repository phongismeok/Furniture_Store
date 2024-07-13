package com.example.asm_mvvm.response

import com.example.asm_mvvm.models.Cart
import com.google.gson.annotations.SerializedName

data class CartResponse (
    @SerializedName("_id") val id: String,
    @SerializedName("productId") val productId: String,
    @SerializedName("productName") val productName: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("image") val image: String,
    @SerializedName("price") val price: Double,
){
    fun toCart(): Cart {
        return Cart(
            id = this.id,
            productId = this.productId,
            productName = this.productName,
            quantity = this.quantity,
            image = this.image,
            price = this.price
        )
    }

}