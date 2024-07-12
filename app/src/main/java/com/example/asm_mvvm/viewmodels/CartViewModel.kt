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

    init {
        getCart()
    }

    fun getCart() {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().cartService.getListCart()
                if (response.isSuccessful) {
                    _cart.postValue(response.body()?.map { it.toCart() })
                    Log.d("check", "getCart: ok")
                } else {
                    _cart.postValue(emptyList())
                    Log.d("check", "getCart: fail1")
                }
            } catch (e: Exception) {
                _cart.postValue(emptyList())
                Log.d("check", "getCart: $e")
            }

        }
    }

    fun getCartsByProductId(id: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().cartService.getCartByProductId(id)
                Log.d("TAG", "getCartByPrdId: $response")

                if (response.isSuccessful) {
                    _cart.postValue(response.body()?.map { it.toCart() })
                } else {
                    _cart.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "getCartByPrdId: " + e.message)
                _cart.postValue(emptyList())
            }
        }
    }

    fun addProductToCart(
        id: String,
        cartRequest: CartRequest,
        successfulNotification: String,
        failureNotification: String,
        context: Context
    ) {
        viewModelScope.launch {
            cartRequest.id =null

            val response = RetrofitBase().cartService.addProductToCart(cartRequest)
            if (response.isSuccessful) {
                getCartsByProductId(id = id)
                Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
            }
        }
//        viewModelScope.launch {
//            try {
//                val response = RetrofitBase().cartService.addProductToCart(cart)
//                if (response.isSuccessful) {
//                    getCartsByProductId(id = id)
//                    Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
//                    Log.d("TAG", "addProductToCart: ok")
//                } else {
//                    Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
//                    Log.d("TAG", "addProductToCart: loi ")
//                }
//            } catch (e: Exception) {
//                // Xử lý lỗi
//                Log.e("TAG", "AddProductToCart: " + e.message)
//            }
//        }
    }

    fun updateQuantityCart(
        productId: String,
        quantity: Int,
        successfulNotification: String,
        failureNotification: String,
        context: Context,
        type: Int
    ) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitBase().cartService.updateQuantityCart(productId, quantity)
                Log.d("TAG", "UpdateQuantity: $response")
                if (response.isSuccessful) {
                    getCart()
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

    fun deleteCart(id: String,context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().cartService.deleteCart(id)
                if (response.isSuccessful) {
                    getCart()
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