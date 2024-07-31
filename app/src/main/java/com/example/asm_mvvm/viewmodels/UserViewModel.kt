package com.example.asm_mvvm.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.models.Product
import com.example.asm_mvvm.models.User
import com.example.asm_mvvm.request.FavoritesRequest
import com.example.asm_mvvm.request.UserRequest
import com.example.asm_mvvm.response.FavoritesResponse
import com.example.asm_mvvm.response.UserResponse
import com.example.asm_mvvm.retrofit.RetrofitBase

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class UserViewModel() : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFailed = MutableLiveData<Boolean>()
    val isFailed: LiveData<Boolean> get() = _isFailed

    fun login(context: Context, email: String, password: String,checked:String, onResult: (Boolean) -> Unit) {
        SharedPreferencesManager.init(context)
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                                SharedPreferencesManager.saveEmail(email)
                                SharedPreferencesManager.savePassword(password)
                                SharedPreferencesManager.saveChecked(checked)
                            onResult(true)
                        } else {
                            onResult(false)
                        }
                    }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun signUp(email: String, password: String,name:String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userBody = UserRequest(
                                username = email,
                                password = password,
                                avatar = "https://cellphones.com.vn/sforum/wp-content/uploads/2023/10/avatar-trang-4.jpg",
                                name = name
                            )
                            addUser(userBody)
                            onResult(true)
                        } else {
                            onResult(false)
                        }
                    }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun getEmailFromSharedPreferences(): String? {
        return SharedPreferencesManager.getEmail()
    }

    fun getPassFromSharedPreferences(): String? {
        return SharedPreferencesManager.getPassword()
    }

    fun getCheckFromSharedPreferences(): String? {
        return SharedPreferencesManager.getChecked()
    }


    fun getUserByUsername(username: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().userService.getUserByUserName(username)
                if (response.isSuccessful && response.body() != null) {
                    _user.value = response.body()
                    _isFailed.value = false
                } else {
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addUser(
        userRequest: UserRequest,
    ) {
        viewModelScope.launch {
            userRequest.id =null
            val response = RetrofitBase().userService.addUser(userRequest)
            if (response.isSuccessful) {
                Log.e("TAG", "add ok")
            }else{
                Log.e("TAG", "add false")
            }
        }
    }

}