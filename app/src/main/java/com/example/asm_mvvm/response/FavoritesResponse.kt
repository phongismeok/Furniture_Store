package com.example.asm_mvvm.response

import com.example.asm_mvvm.models.Favorites
import com.google.gson.annotations.SerializedName

data class FavoritesResponse (
    @SerializedName("_id") val id: String,
    @SerializedName("productId") val productId: String,
    @SerializedName("productName") val productName: String,
    @SerializedName("image") val image: String,
    @SerializedName("price") val price: Double,
    @SerializedName("account") val account: String
){
    fun toFavorites(): Favorites {
        return Favorites(
            id = this.id,
            productId = this.productId,
            productName = this.productName,
            image = this.image,
            price = this.price,
            account = this.account
        )
    }
}
