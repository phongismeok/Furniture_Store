package com.example.asm_mvvm.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.models.Favorites
import com.example.asm_mvvm.models.Notification
import com.example.asm_mvvm.request.FavoritesRequest
import com.example.asm_mvvm.request.NotificationRequest
import com.example.asm_mvvm.response.FavoritesResponse
import com.example.asm_mvvm.retrofit.RetrofitBase
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModel(){
    private val _notification = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> = _notification

    fun getNotificationByAccount(account: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().notificationService.getNotificationByAccount(account)
                Log.d("TAG", "getNotification: $response")

                if (response.isSuccessful) {
                    _notification.postValue(response.body()?.map { it.toNotification() })
                } else {
                    _notification.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "getNotification: " + e.message)
                _notification.postValue(emptyList())
            }
        }
    }

    fun addNotification(
        account: String,
        notificationRequest: NotificationRequest,
        successfulNotification: String,
        failureNotification: String,
        context: Context
    ) {
        viewModelScope.launch {
            notificationRequest.id =null

            val response = RetrofitBase().notificationService.addNotification(notificationRequest)
            if (response.isSuccessful) {
                getNotificationByAccount(account)
                Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
            }
        }
    }

}