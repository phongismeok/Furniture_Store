package com.example.asm_mvvm.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var isLoading by mutableStateOf(false)
    var isFailed by mutableStateOf(false)

    private fun getOrderByAccount(
        account: String
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitBase().orderService.getOrderByAccount(account)
                if (response.isSuccessful) {
                    _order.postValue(response.body()?.map { it.toOrder() })
                    isFailed = false
                } else {
                    _order.postValue(emptyList())
                    isFailed = true
                }
            } catch (e: Exception) {
                _order.postValue(emptyList())
                isFailed = true
            } finally {
                isLoading = false
            }
        }
    }

    fun getOrderByState(
        account: String,
        state: String,
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitBase().orderService.getOrderByState(account, state)
                if (response.isSuccessful) {
                    _order.postValue(response.body()?.map { it.toOrder() })
                    isFailed = false
                } else {
                    _order.postValue(emptyList())
                    isFailed = true
                }
            } catch (e: Exception) {
                _order.postValue(emptyList())
                isFailed = true
            } finally {
                isLoading = false
            }
        }
    }

    fun addOrder(
        account: String,
        orderRequest: OrderRequest
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                orderRequest.id = null
                val response = RetrofitBase().orderService.addOrder(orderRequest)
                if (response.isSuccessful) {
                    getOrderByAccount(account)
                    isFailed = false
                } else {
                    isFailed = true
                }
            } catch (_: Exception) {
                isFailed = true
            } finally {
                isLoading = false
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
            isLoading = true
            try {
                val response = RetrofitBase().orderService.updateStateOrder(id, state)
                if (response.isSuccessful) {
                    getOrderByState(account, stateHt)
                    Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
                    isFailed = false
                } else {
                    Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
                    isFailed = true
                }
            } catch (e: Exception) {
                isFailed = true
            } finally {
                isLoading = false
            }
        }
    }

    fun deleteOrder(id: String, account: String, context: Context) {
        viewModelScope.launch {
            try {
                isLoading = true
                val response = RetrofitBase().orderService.deleteOrder(id)
                if (response.isSuccessful) {
                    getOrderByState(account, "Canceled")
                    Toast.makeText(context, R.string.delete_order_success_en , Toast.LENGTH_SHORT).show()
                    isFailed = false
                } else {
                    Toast.makeText(context, R.string.delete_order_fail_en, Toast.LENGTH_SHORT).show()
                    isFailed = true
                }
            } catch (e: Exception) {
                isFailed = true
            } finally {
                isLoading = false
            }
        }
    }
}