package com.example.asm_mvvm.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.request.PushNotificationRequest
import com.example.asm_mvvm.retrofit.RetrofitBase
import kotlinx.coroutines.launch

class PushNotificationViewModel : ViewModel() {
    fun sendNotification(
        pushNotificationRequest: PushNotificationRequest
    ) {
        viewModelScope.launch {
            val response = RetrofitBase().pushNotificationService.sendNotification(pushNotificationRequest)
            if (response.isSuccessful) {
                Log.d("checkntf", "sendNotification: ok")
            }else{
                Log.d("checkntf", "sendNotification: fail")
            }
        }
    }
}