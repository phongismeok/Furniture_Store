package com.example.asm_mvvm.retrofit

import com.example.asm_mvvm.request.CommentRequest
import com.example.asm_mvvm.response.CommentResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService {
    @GET("get-list-comment-by-account/{account}")
    suspend fun getCommentByAccount(@Path("account") account: String): Response<List<CommentResponse>>

    @GET("get-list-comment-by-productId/{productId}")
    suspend fun getCommentByProductId(@Path("productId") productId: String): Response<List<CommentResponse>>

    @POST("add-comment")
    suspend fun addComment(@Body commentRequest: CommentRequest): Response<Void>

    @DELETE("delete-comment/{id}")
    suspend fun deleteComment(@Path("id") id: String): Response<ResponseBody>
}