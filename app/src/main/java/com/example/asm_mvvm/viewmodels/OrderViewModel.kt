package com.example.asm_mvvm.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            try {
                val response = RetrofitBase().orderService.getOrderByAccount(account)
                if (response.isSuccessful) {
                    _order.postValue(response.body()?.map { it.toOrder() })
                    isLoading = true
                } else {
                    _order.postValue(emptyList())
                    isLoading = true
                }
            } catch (e: Exception) {
                _order.postValue(emptyList())
                isFailed = true
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
            orderRequest.id = null

            val response = RetrofitBase().orderService.addOrder(orderRequest)
            if (response.isSuccessful) {
                getOrderByAccount(account)
            } else {
                Log.d("fixloi1", "addOrder: loi")
            }
        }
    }

    fun updateStateOrder(
        id: String,
        state: String,
        stateHt: String,
        successfulNotification: String,
        failureNotification: String,
        context: Context,
        account: String
    ) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitBase().orderService.updateStateOrder(id, state)
                Log.d("TAG", "UpdateOrder: $response")
                if (response.isSuccessful) {
                    getOrderByState(account, stateHt)
                    Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
                }


            } catch (e: Exception) {
                Log.e("TAG", "UpdateOrder error: " + e.message)
            }
        }
    }


    fun deleteOrder(id: String, account: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().orderService.deleteOrder(id)
                if (response.isSuccessful) {
                    getOrderByState(account, "Canceled")
                    Toast.makeText(context, "xoá đơn hàng thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "xoá đơn hàng thất bại", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("TAG", "DeleteOrder error: " + e.message)
            }
        }
    }
}