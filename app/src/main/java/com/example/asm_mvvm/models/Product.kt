package com.example.asm_mvvm.models

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("_id") val _id: String,
    @SerializedName("productId") val productId: String,
    @SerializedName("productName") val productName: String,
    @SerializedName("price") val price: Double,
    @SerializedName("describe") val describe: String,
    @SerializedName("image1") val image1: String,
    @SerializedName("image2") val image2: String,
    @SerializedName("image3") val image3: String,
    @SerializedName("type") val type: Int,
    @SerializedName("stateFavorites") val stateFavorites: Int,
    @SerializedName("typeProduct") val typeProduct: String,
){
    fun toProduct(): Product {
        return Product(
            _id = this._id,
            productId = this.productId,
            productName = this.productName,
            price = this.price,
            describe = this.describe,
            image1 = this.image1,
            image2 = this.image2,
            image3 = this.image3,
            type = this.type,
            stateFavorites = this.stateFavorites,
            typeProduct = this.typeProduct
        )
    }

}