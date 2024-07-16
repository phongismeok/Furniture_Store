package com.example.asm_mvvm.retrofit

import com.example.asm_mvvm.response.ProvinceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ProvinceService {
    @GET("shiip/public-api/master-data/province")
    fun getProvinces(
        @Header("Token") token: String,
        @Header("ShopId") shopId: String
    ): Call<ProvinceResponse>
}