package com.example.asm_mvvm.response

import com.example.asm_mvvm.models.Comment
import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("productId") val productId: String,
    @SerializedName("content") val content: String,
    @SerializedName("rate") val rate: Double,
    @SerializedName("account") val account: String,
    @SerializedName("avatar") val avatar: String
) {
    fun toComment(): Comment {
        return Comment(
            id = this.id,
            productId = this.productId,
            content = this.content,
            rate = this.rate,
            account = this.account,
            avatar = this.avatar
        )
    }

}
