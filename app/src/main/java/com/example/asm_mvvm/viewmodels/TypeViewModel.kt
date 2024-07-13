package com.example.asm_mvvm.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.models.Type
import com.example.asm_mvvm.retrofit.RetrofitBase
import kotlinx.coroutines.launch

class TypeViewModel : ViewModel() {
    private val _type = MutableLiveData<List<Type>>()

    val types: LiveData<List<Type>> = _type

    init {
        getType()
    }

    private fun getType() {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().typeService.getListType()
                if (response.isSuccessful) {
                    _type.postValue(response.body()?.map { it.toType() })
                    Log.d("check", "getType: ok")
                } else {
                    _type.postValue(emptyList())
                    Log.d("check", "getType: fail1")
                }
            } catch (e: Exception) {
                _type.postValue(emptyList())
                Log.d("check", "getType: $e ")
            }
        }
    }
}