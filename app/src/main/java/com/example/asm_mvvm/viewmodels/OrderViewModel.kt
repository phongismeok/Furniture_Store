package com.example.asm_mvvm.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.R
import com.example.asm_mvvm.models.Order
import com.example.asm_mvvm.request.OrderRequest
import com.example.asm_mvvm.retrofit.RetrofitBase
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    private val _order = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _order

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFailed = MutableLiveData<Boolean>()
    val isFailed: LiveData<Boolean> get() = _isFailed

    private fun getOrderByAccount(
        account: String
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().orderService.getOrderByAccount(account)
                if (response.isSuccessful) {
                    _order.postValue(response.body()?.map { it.toOrder() })
                    _isFailed.value = false
                } else {
                    _order.postValue(emptyList())
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _order.postValue(emptyList())
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getOrderByState(
        account: String,
        state: String,
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().orderService.getOrderByState(account, state)
                if (response.isSuccessful) {
                    _order.postValue(response.body()?.map { it.toOrder() })
                    _isFailed.value = false
                } else {
                    _order.postValue(emptyList())
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _order.postValue(emptyList())
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addOrder(
        account: String,
        orderRequest: OrderRequest
    ) {
        viewModelScope.launch {
            try {
                orderRequest.id = null
                val response = RetrofitBase().orderService.addOrder(orderRequest)
                if (response.isSuccessful) {
                    getOrderByAccount(account)
                } else {
                    Log.d("TAG", "deleteOrder: loi ")
                }
            } catch (e: Exception) {
                Log.d("TAG", "deleteOrder: loi $e")
            }
        }
    }

    fun updateStateOrder(
        id: String,
        state: String,
        stateHt: String,
        successfulNotification: Int,
        failureNotification: Int,
        context: Context,
        account: String
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().orderService.updateStateOrder(id, state)
                if (response.isSuccessful) {
                    getOrderByState(account, stateHt)
                    Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.d("TAG", "deleteOrder: loi $e")
            }
        }
    }

    fun deleteOrder(id: String, account: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().orderService.deleteOrder(id)
                if (response.isSuccessful) {
                    getOrderByState(account, "Canceled")
                    Toast.makeText(context, R.string.delete_order_success_en , Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, R.string.delete_order_fail_en, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.d("TAG", "deleteOrder: loi $e")
            }
        }
    }
}