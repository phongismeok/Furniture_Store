package com.example.asm_mvvm.response

import com.example.asm_mvvm.models.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("productName") val productName: String,
    @SerializedName("price") val price: Double,
    @SerializedName("describe") val describe: String,
    @SerializedName("image1") val image1: String,
    @SerializedName("image2") val image2: String,
    @SerializedName("image3") val image3: String,
    @SerializedName("type") val type: Int,
    @SerializedName("typeProduct") val typeProduct: String,
    @SerializedName("rate") val rate: String
) {
    fun toProduct(): Product {
        return Product(
            id = this.id,
            productName = this.productName,
            price = this.price,
            describe = this.describe,
            image1 = this.image1,
            image2 = this.image2,
            image3 = this.image3,
            type = this.type,
            typeProduct = this.typeProduct,
            rate = this.rate
        )
    }
}