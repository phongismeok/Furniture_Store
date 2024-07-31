package com.example.asm_mvvm.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.models.Cart
import com.example.asm_mvvm.request.CartRequest
import com.example.asm_mvvm.response.CartResponse
import com.example.asm_mvvm.retrofit.RetrofitBase
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val _carts = MutableLiveData<List<Cart>>()
    val carts: LiveData<List<Cart>> = _carts

    private val _cart = MutableLiveData<CartResponse>()
    val cart: LiveData<CartResponse> = _cart

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFailed = MutableLiveData<Boolean>()
    val isFailed: LiveData<Boolean> get() = _isFailed

    fun getListCartsByAccount(account: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().cartService.getCartByAccount(account)
                if (response.isSuccessful) {
                    _carts.postValue(response.body()?.map { it.toCart() })
                    _isFailed.value = false
                } else {
                    _carts.postValue(emptyList())
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _carts.postValue(emptyList())
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getCartByProductId(account: String, id: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().cartService.getCartByProductId(account, id)
                if (response.isSuccessful && response.body() != null) {
                    _cart.value = response.body()
                    _isFailed.value = false
                } else {
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addProductToCart(
        account: String,
        cartRequest: CartRequest,
        successfulNotification: String,
        failureNotification: String,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                cartRequest.id = null
                val response = RetrofitBase().cartService.addProductToCart(cartRequest)
                if (response.isSuccessful) {
                    getListCartsByAccount(account)
                    Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("TAG", "add error: " + e.message)
            }

        }
    }

    fun updateQuantityCart(
        id: String,
        quantity: Int,
        account: String,
        successfulNotification: String,
        failureNotification: String,
        context: Context,
        toastText: Boolean
    ) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitBase().cartService.updateQuantityCart(id, quantity)
                Log.d("TAG", "UpdateQuantity: $response")
                if (response.isSuccessful) {
                    getListCartsByAccount(account)
                    if (toastText) {
                        Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (toastText) {
                        Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("TAG", "UpdateQuantity error: " + e.message)
            }
        }
    }

    fun deleteCart(id: String, account: String, context: Context, toastText: Boolean) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().cartService.deleteCart(id)
                if (response.isSuccessful) {
                    getListCartsByAccount(account)
                    if (toastText) {
                        Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (toastText) {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("TAG", "DeleteCart error: " + e.message)
            }
        }
    }

}