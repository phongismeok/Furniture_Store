package com.example.asm_mvvm.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.models.Product
import com.example.asm_mvvm.retrofit.RetrofitBase
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _product = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _product

    private val _product2 = MutableLiveData<Product?>()
    val product: LiveData<Product?> = _product2

    init {
//        getProduct()
    }

    fun getProduct() {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().productService.getListPro()
                if (response.isSuccessful) {
                    _product.postValue(response.body()?.map { it.toProduct() })
                    Log.d("check", "getProduct: ok")
                } else {
                    _product.postValue(emptyList())
                    Log.d("check", "getProduct: fail1")
                }
            } catch (e: Exception) {
                _product.postValue(emptyList())
                Log.d("check", "getProduct: $e")
            }
        }
    }

    fun getProductsByType(type: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().productService.getProductsByType(type)
                Log.d("TAG", "getProductsByType: $response")

                if (response.isSuccessful) {
                    _product.postValue(response.body()?.map { it.toProduct() })
                } else {
                    _product.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "getProductsByType: " + e.message)
                _product.postValue(emptyList())
            }
        }
    }

    fun getProductsByTypeProduct(type: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().productService.getProductsByTypeProduct(type)
                Log.d("TAG", "getProductsByTypePro: $response")

                if (response.isSuccessful) {
                    _product.postValue(response.body()?.map { it.toProduct() })
                } else {
                    _product.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "getProductsByTypePro: " + e.message)
                _product.postValue(emptyList())
            }
        }
    }

    fun getProductById(id: String) {
        viewModelScope.launch {
            try {
                val res = RetrofitBase().productService.getProductById(id)
                _product2.postValue(res.toProduct())
            } catch (e: Exception) {
                Log.e("TAG", "getProductById: " + e.message)
                _product2.postValue(null)
            }
        }
    }

    fun getProductsByStateFavorites(stateFavorites: Int) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitBase().productService.getProductsByStateFavorites(stateFavorites)
                Log.d("TAG", "getProductsByStateFavorites: $response")

                if (response.isSuccessful) {
                    _product.postValue(response.body()?.map { it.toProduct() })
                } else {
                    _product.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "getProductsByStateFavorites: " + e.message)
                _product.postValue(emptyList())
            }
        }
    }

    fun updateStateFavorites(
        productId: String,
        stateFavorites: Int,
        successfulNotification: String,
        failureNotification: String,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitBase().productService.updateStateFavorites(productId, stateFavorites)
                Log.d("TAG", "UpdateStateFavorites: $response")
                if (response.isSuccessful) {
                    getProductsByStateFavorites(1)
                    Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("TAG", "UpdateStateFavorites error: " + e.message)
            }
        }
    }

}

