package com.example.asm_mvvm.retrofit

import com.example.asm_mvvm.models.User
import com.example.asm_mvvm.request.UserRequest
import com.example.asm_mvvm.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("get-user-by-username/{username}")
    suspend fun getUserByUserName(
        @Path("username") username: String,
    ): Response<UserResponse>

    @POST("add-user")
    suspend fun addUser(@Body userRequest: UserRequest): Response<Void>
}