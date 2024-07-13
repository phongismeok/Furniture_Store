package com.example.asm_mvvm.retrofit

import com.example.asm_mvvm.response.TypeResponse
import retrofit2.Response
import retrofit2.http.GET

interface TypeService {
    @GET("get-list-types")
    suspend fun getListType(): Response<List<TypeResponse>>
}