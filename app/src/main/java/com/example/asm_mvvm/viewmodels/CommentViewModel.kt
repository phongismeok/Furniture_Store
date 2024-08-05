package com.example.asm_mvvm.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.models.Comment
import com.example.asm_mvvm.request.CommentRequest
import com.example.asm_mvvm.retrofit.RetrofitBase
import kotlinx.coroutines.launch

class CommentViewModel : ViewModel() {
    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> = _comments

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFailed = MutableLiveData<Boolean>()
    val isFailed: LiveData<Boolean> get() = _isFailed

    fun getCommentByAccount(account: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().commentService.getCommentByAccount(account)
                if (response.isSuccessful) {
                    _comments.postValue(response.body()?.map { it.toComment() })
                    _isFailed.value = false
                } else {
                    _comments.postValue(emptyList())
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _comments.postValue(emptyList())
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getCommentByProductId(productId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().commentService.getCommentByProductId(productId)
                if (response.isSuccessful) {
                    _comments.postValue(response.body()?.map { it.toComment() })
                    _isFailed.value = false
                } else {
                    _comments.postValue(emptyList())
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _comments.postValue(emptyList())
                _isFailed.value = true
                Log.d("fixloi", "getCommentByProductId: loi $e ")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addCommentToProduct(
        productId: String,
        commentRequest: CommentRequest,
        successfulNotification: String,
        failureNotification: String,
        context: Context
    ) {
        viewModelScope.launch {
            commentRequest.id =null

            val response = RetrofitBase().commentService.addComment(commentRequest)
            if (response.isSuccessful) {
                getCommentByProductId(productId)
                Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteComment(id: String,context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().commentService.deleteComment(id)
                if (response.isSuccessful) {
                    Toast.makeText(context, "xoá bình luận thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "xoá bình luận thất bại", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("TAG", "DeleteFv error: " + e.message)
            }
        }
    }
}