package com.example.asm_mvvm.retrofit

import com.example.asm_mvvm.response.DistrictResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DistrictService {
    @GET("shiip/public-api/master-data/district")
    fun getDistricts(
        @Header("Token") token: String,
        @Header("ShopId") shopId: String,
        @Query("province_id") ProvinceID: Int
    ): Call<DistrictResponse>
}