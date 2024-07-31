package com.example.asm_mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.models.Product
import com.example.asm_mvvm.retrofit.RetrofitBase
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _product = MutableLiveData<Product?>()
    val product: LiveData<Product?> = _product

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFailed = MutableLiveData<Boolean>()
    val isFailed: LiveData<Boolean> get() = _isFailed

    fun getProduct() {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().productService.getListPro()
                if (response.isSuccessful) {
                    _products.postValue(response.body()?.map { it.toProduct() })
                    _isFailed.value = false
                } else {
                    _products.postValue(emptyList())
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _products.postValue(emptyList())
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProductsByType(type: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().productService.getProductsByType(type)
                if (response.isSuccessful) {
                    _products.postValue(response.body()?.map { it.toProduct() })
                    _isFailed.value = false
                } else {
                    _products.postValue(emptyList())
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _products.postValue(emptyList())
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProductsByTypeProduct(type: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().productService.getProductsByTypeProduct(type)
                if (response.isSuccessful) {
                    _products.postValue(response.body()?.map { it.toProduct() })
                    _isFailed.value = false
                } else {
                    _products.postValue(emptyList())
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _products.postValue(emptyList())
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProductById(id: String) {
        viewModelScope.launch {
            try {
                val res = RetrofitBase().productService.getProductById(id)
                _product.postValue(res.toProduct())
                _isFailed.value = false
            } catch (e: Exception) {
                _product.postValue(null)
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchProduct(key: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().productService.searchProduct(key)
                if (response.isSuccessful) {
                    _products.postValue(response.body()?.map { it.toProduct() })
                    _isFailed.value = false
                } else {
                    _products.postValue(emptyList())
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _products.postValue(emptyList())
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

}

