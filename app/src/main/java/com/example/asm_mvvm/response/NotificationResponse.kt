package com.example.asm_mvvm.response

import com.example.asm_mvvm.models.Favorites
import com.example.asm_mvvm.models.Notification
import com.google.gson.annotations.SerializedName

data class NotificationResponse (
    @SerializedName("_id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("state") val state: Int,
    @SerializedName("image") val image: String,
    @SerializedName("account") val account: String
){
    fun toNotification(): Notification {
        return Notification(
            id = this.id,
            title = this.title,
            content = this.content,
            state = this.state,
            image = this.image,
            account = this.account
        )
    }
}