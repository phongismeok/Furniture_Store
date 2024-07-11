package com.example.asm_mvvm.retrofit

import com.example.asm_mvvm.models.Product
import com.example.asm_mvvm.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("get-user-by-username/{username}")
    suspend fun getUserByUsername(@Path("username") username: String): Response<List<User>>
}