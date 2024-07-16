package com.example.asm_mvvm.retrofit

import com.example.asm_mvvm.response.DistrictResponse
import com.example.asm_mvvm.response.WardResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WardService {
    @GET("shiip/public-api/master-data/ward")
    fun getWards(
        @Header("Token") token: String,
        @Header("ShopId") shopId: String,
        @Query("district_id") districtId: Int
    ): Call<WardResponse>
}