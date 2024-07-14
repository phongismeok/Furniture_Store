package com.example.asm_mvvm.response

import com.example.asm_mvvm.models.Shipping
import com.google.gson.annotations.SerializedName

data class ShippingResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("account") val account: String,
    @SerializedName("select") val select: Int
) {
    fun toShip(): Shipping {
        return Shipping(
            id = this.id,
            name = this.name,
            address = this.address,
            account = this.account,
            select = this.select
        )
    }
}