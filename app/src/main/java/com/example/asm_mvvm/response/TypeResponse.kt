package com.example.asm_mvvm.response

import com.example.asm_mvvm.models.Type
import com.google.gson.annotations.SerializedName

data class TypeResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
) {
    fun toType(): Type {
        return Type(
            id = this.id,
            name = this.name,
            image = this.image,
        )
    }
}