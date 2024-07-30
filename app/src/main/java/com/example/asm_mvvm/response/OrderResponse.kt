package com.example.asm_mvvm.response

import com.example.asm_mvvm.models.Notification
import com.example.asm_mvvm.models.Order
import com.google.gson.annotations.SerializedName

class OrderResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("productId") val productId: String,
    @SerializedName("productName") val productName: String,
    @SerializedName("image") val image: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("price") val price: Double,
    @SerializedName("state") val state: String,
    @SerializedName("account") val account: String,
    @SerializedName("createdAt") val createdAt: String
) {
    fun toOrder(): Order {
        return Order(
            id = this.id,
            productId = this.productId,
            productName = this.productName,
            image = this.image,
            quantity = this.quantity,
            price = this.price,
            state = this.state,
            account = this.account,
            createdAt = this.createdAt
        )
    }
}