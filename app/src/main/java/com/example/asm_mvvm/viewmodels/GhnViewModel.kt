package com.example.asm_mvvm.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.models.District
import com.example.asm_mvvm.models.Province
import com.example.asm_mvvm.models.Ward
import com.example.asm_mvvm.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.await

class GhnViewModel : ViewModel() {
    private val _provinces = MutableLiveData<List<Province>>()
    val provinces: LiveData<List<Province>> = _provinces

    private val _districts = MutableLiveData<List<District>>()
    val districts: LiveData<List<District>> = _districts

    private val _wards = MutableLiveData<List<Ward>>()
    val wards: LiveData<List<Ward>> = _wards

    private val token = "536fc722-ed26-11ee-b1d4-92b443b7a897"
    private val shopId = "191567"

    fun fetchProvinces() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.provinceService.getProvinces(token, shopId).await()
                _provinces.postValue(response.data)
            } catch (e: Exception) {
                // Handle error
                println("fetchProvinces: error\n$e")
            }
        }
    }

    fun fetchDistricts(provinceId : Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.districtService.getDistricts(token, shopId, provinceId).await()
                _districts.postValue(response.data)
            } catch (e: Exception) {
                // Handle error
                println("fetchProvinces: error\n$e")
            }
        }
    }

    fun fetchWards(districtId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.wardService.getWards(token, shopId, districtId).await()
                _wards.postValue(response.data)
            } catch (e: Exception) {
                // Handle error
                println("fetchWards: error\n$e")
            }
        }
    }
}