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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFailed = MutableLiveData<Boolean>()
    val isFailed: LiveData<Boolean> get() = _isFailed

    init {
        getType()
    }

    private fun getType() {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().typeService.getListType()
                if (response.isSuccessful) {
                    _type.postValue(response.body()?.map { it.toType() })
                    _isFailed.value = false
                } else {
                    _type.postValue(emptyList())
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _type.postValue(emptyList())
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}