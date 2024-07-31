package com.example.asm_mvvm.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.models.Notification
import com.example.asm_mvvm.request.NotificationRequest
import com.example.asm_mvvm.retrofit.RetrofitBase
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModel(){

    private val _notification = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> = _notification

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFailed = MutableLiveData<Boolean>()
    val isFailed: LiveData<Boolean> get() = _isFailed

    fun getNotificationByAccount(account: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().notificationService.getNotificationByAccount(account)
                if (response.isSuccessful) {
                    _notification.postValue(response.body()?.map { it.toNotification() })
                    _isFailed.value = false
                } else {
                    _notification.postValue(emptyList())
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _notification.postValue(emptyList())
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addNotification(
        account: String,
        notificationRequest: NotificationRequest,
    ) {
        viewModelScope.launch {
            notificationRequest.id =null

            val response = RetrofitBase().notificationService.addNotification(notificationRequest)
            if (response.isSuccessful) {
                getNotificationByAccount(account)
            }else{
                Log.d("fixloi1", "addNotification: loi")
            }
        }
    }

    fun searchNotification(key: String,account:String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().notificationService.searchNotification(key,account)
                if (response.isSuccessful) {
                    _notification.postValue(response.body()?.map { it.toNotification() })
                    _isFailed.value = false
                } else {
                    _notification.postValue(emptyList())
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _notification.postValue(emptyList())
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteNotifications(id: String,account: String,context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().notificationService.deleteNotification(id)
                if (response.isSuccessful) {
                    getNotificationByAccount(account)
                    Toast.makeText(context, "xoá thông báo thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "xoá thông báo thất bại", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("TAG", "DeleteNoti error: " + e.message)
            }
        }
    }

}