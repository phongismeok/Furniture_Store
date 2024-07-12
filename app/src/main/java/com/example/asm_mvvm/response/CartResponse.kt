package com.example.asm_mvvm.response

import com.example.asm_mvvm.models.Cart
import com.google.gson.annotations.SerializedName

data class CartResponse (
    @SerializedName("_id") val id: String,
    @SerializedName("productId") val productId: String,
    @SerializedName("quantity") val quantity: Int,
){
    fun toCart(): Cart {
        return Cart(
            id = this.id,
            productId = this.productId,
            quantity = this.quantity
        )
    }

}