package com.example.asm_mvvm.retrofit

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://dev-online-gateway.ghn.vn/"
    private const val TOKEN = "536fc722-ed26-11ee-b1d4-92b443b7a897"
    private const val SHOP_ID = "191567"

    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val request: Request = chain.request().newBuilder()
            .addHeader("Token", TOKEN)
            .addHeader("ShopId", SHOP_ID)
            .build()
        chain.proceed(request)
    }.build()

    val provinceService: ProvinceService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProvinceService::class.java)
    }

    val districtService: DistrictService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DistrictService::class.java)
    }

    val wardService: WardService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WardService::class.java)
    }
}