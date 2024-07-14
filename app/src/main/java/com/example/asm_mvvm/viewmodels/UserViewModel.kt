package com.example.asm_mvvm.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.models.Product
import com.example.asm_mvvm.models.User
import com.example.asm_mvvm.retrofit.RetrofitBase

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class UserViewModel() : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _user = MutableLiveData<List<User>>()
    val user: LiveData<List<User>> = _user

    fun login(context: Context, email: String, password: String,save:Boolean, onResult: (Boolean) -> Unit) {
        SharedPreferencesManager.init(context)
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            if (save){
                                SharedPreferencesManager.saveEmail(email)
                                SharedPreferencesManager.savePassword(password)
                            }
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

    fun signUp(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
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


    fun getUserByUsername(username: String) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitBase().userService.getUserByUsername(username)
                Log.d("TAG", "getUserByUsername: $response")

                if (response.isSuccessful) {
                    _user.postValue(response.body()?.map { it.toUser() })
                } else {
                    _user.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "getUserByUsername: " + e.message)
                _user.postValue(emptyList())
            }
        }
    }

}