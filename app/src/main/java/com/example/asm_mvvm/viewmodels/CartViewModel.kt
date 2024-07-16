package com.example.asm_mvvm.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.models.Cart
import com.example.asm_mvvm.models.Product
import com.example.asm_mvvm.request.CartRequest
import com.example.asm_mvvm.response.CartResponse
import com.example.asm_mvvm.retrofit.RetrofitBase
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CartViewModel : ViewModel() {
    private val _cart = MutableLiveData<List<Cart>>()
    val carts: LiveData<List<Cart>> = _cart

    private val _cart2 = MutableLiveData<CartResponse>()
    val carts2: LiveData<CartResponse> = _cart2


    fun getCartByAccount(account: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().cartService.getCartByAccount(account)
                Log.d("TAG", "getCart: $response")

                if (response.isSuccessful) {
                    _cart.postValue(response.body()?.map { it.toCart() })
                } else {
                    _cart.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "getCart: " + e.message)
                _cart.postValue(emptyList())
            }
        }
    }

    fun getCartsByProductId(account: String,id: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().cartService.getCartByProductId(account,id)
                if (response.isSuccessful && response.body() != null) {
                    _cart2.value = response.body()
                } else {
                    Log.d("check", "getFvByIdPro: fail1")
                }
            } catch (e: Exception) {
//                Log.e("TAG", "getCartByPrdId: " + e.message)
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
            cartRequest.id =null
            val response = RetrofitBase().cartService.addProductToCart(cartRequest)
            if (response.isSuccessful) {
                getCartByAccount(account)
                Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
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
        type: Int
    ) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitBase().cartService.updateQuantityCart(id, quantity)
                Log.d("TAG", "UpdateQuantity: $response")
                if (response.isSuccessful) {
                    getCartByAccount(account)
                    if(type==1){
                        Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (type == 1) {
                        Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("TAG", "UpdateQuantity error: " + e.message)
            }
        }
    }

    fun deleteCart(id: String,account: String,context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().cartService.deleteCart(id)
                if (response.isSuccessful) {
                    getCartByAccount(account)
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("TAG", "DeleteCart error: " + e.message)
            }
        }
    }



}