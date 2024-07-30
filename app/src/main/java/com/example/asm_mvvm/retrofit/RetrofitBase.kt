package com.example.asm_mvvm.retrofit

import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.Collections

class RetrofitBase {
    private val baseUrl = "http://192.168.0.119:3000/api/"

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
    val notificationService: NotificationService by lazy {
        retrofit.create(NotificationService::class.java)
    }
    val orderService: OrderService by lazy {
        retrofit.create(OrderService::class.java)
    }
}