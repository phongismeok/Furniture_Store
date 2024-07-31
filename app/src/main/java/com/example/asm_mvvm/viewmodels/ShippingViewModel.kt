package com.example.asm_mvvm.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.models.Shipping
import com.example.asm_mvvm.request.ShippingRequest
import com.example.asm_mvvm.response.ShippingResponse
import com.example.asm_mvvm.retrofit.RetrofitBase
import com.example.asm_mvvm.screens.activity.ShippingActivity
import kotlinx.coroutines.launch

class ShippingViewModel : ViewModel() {

    private val _ships = MutableLiveData<List<Shipping>>()
    val ships: LiveData<List<Shipping>> = _ships

    private val _ship = MutableLiveData<ShippingResponse>()
    val ship: LiveData<ShippingResponse> = _ship

    fun getShipAddressByAccount(account: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().shippingService.getShippingByAccount(account)
                Log.d("TAG", "getShip: $response")

                if (response.isSuccessful) {
                    _ships.postValue(response.body()?.map { it.toShip() })
                } else {
                    _ships.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "getShip: " + e.message)
                _ships.postValue(emptyList())
            }
        }
    }

    fun getShipAddressBySelect(select: Int,account:String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().shippingService.getShippingBySelect(account,select)
                if (response.isSuccessful && response.body() != null) {
                    _ship.value = response.body()
                } else {
                    Log.d("check", "getShipBySelect: fail1")
                }
            } catch (e: Exception) {
                Log.d("check", "getShipBySelect: fail1 $e" )
            }
        }
    }

    fun updateSelectShipping(
        id: String,
        select: Int,
        successfulNotification: String,
        failureNotification: String,
        context: Context,
        type:Int
    ) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitBase().shippingService.updateSelectShipping(id, select)
                Log.d("TAG", "UpdateSelect: $response")
                if(type == 1){
                    if (response.isSuccessful) {
                        Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                Log.e("TAG", "UpdateSelect error: " + e.message)
            }
        }
    }

    fun addProductToShipping(
        shippingRequest: ShippingRequest,
        successfulNotification: String,
        failureNotification: String,
        context: Context
    ) {
        viewModelScope.launch {
            shippingRequest.id =null

            val response = RetrofitBase().shippingService.addShipping(shippingRequest)
            if (response.isSuccessful) {
                Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
                val intent = Intent(context, ShippingActivity::class.java)
                context.startActivity(intent)
            }else{
                Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
            }
        }
    }

}