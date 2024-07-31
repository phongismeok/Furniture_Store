package com.example.asm_mvvm.models

data class User(
    val id: String,
    val username: String,
    val password: String,
    val avatar: String,
    val name: String
){
    fun toUser(): User {
        return User(
            id = this.id,
            username = this.username,
            password = this.password,
            avatar = this.avatar,
            name = this.name
        )
    }
}