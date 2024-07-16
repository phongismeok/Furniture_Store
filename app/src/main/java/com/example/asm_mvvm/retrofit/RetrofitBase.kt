package com.example.asm_mvvm.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBase {
    private val baseUrl = "http://192.168.0.105:3000/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val productService: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }
    val cartService: CartService by lazy {
        retrofit.create(CartService::class.java)
    }
    val typeService: TypeService by lazy {
        retrofit.create(TypeService::class.java)
    }
    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
    val shippingService: ShippingService by lazy {
        retrofit.create(ShippingService::class.java)
    }
    val favoritesService: FavoritesService by lazy {
        retrofit.create(FavoritesService::class.java)
    }
}