package com.example.asm_mvvm.retrofit

import com.example.asm_mvvm.request.FavoritesRequest
import com.example.asm_mvvm.request.ShippingRequest
import com.example.asm_mvvm.response.FavoritesResponse
import com.example.asm_mvvm.response.ProductResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FavoritesService {
    @GET("get-list-favorites-by-account/{account}")
    suspend fun getFavoritesByAccount(@Path("account") account: String): Response<List<FavoritesResponse>>

    @GET("get-favorite-by-productId/{account}/{productId}")
    suspend fun getFavoriteByProductId(
        @Path("account") account: String,
        @Path("productId") productId: String
    ): Response<FavoritesResponse>

    @GET("search-favorites")
    suspend fun searchFavorites(
        @Query("key") key: String,
        @Query("account") account: String
    ): Response<List<FavoritesResponse>>

    @POST("add-favorite")
    suspend fun addFavorites(@Body favoritesRequest: FavoritesRequest): Response<Void>

    @DELETE("delete-favorite/{id}")
    suspend fun deleteFavorites(@Path("id") id: String): Response<ResponseBody>
}