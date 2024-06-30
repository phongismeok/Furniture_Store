package com.example.asm_mvvm.models

import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("_id") val _id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
){
    fun toType(): Type {
        return Type(
            _id = this._id,
            name = this.name,
            image = this.image,
        )
    }
}