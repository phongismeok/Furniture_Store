package com.example.asm_mvvm.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.models.Product
import com.example.asm_mvvm.models.Shipping
import com.example.asm_mvvm.request.FavoritesRequest
import com.example.asm_mvvm.request.ShippingRequest
import com.example.asm_mvvm.retrofit.RetrofitBase
import com.example.asm_mvvm.screens.activity.ShippingActivity
import com.example.asm_mvvm.screens.activity.SignUpActivity
import kotlinx.coroutines.launch

class ShippingViewModel : ViewModel() {
    private val _ship = MutableLiveData<List<Shipping>>()
    val ships: LiveData<List<Shipping>> = _ship

    fun getShipping() {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().shippingService.getListShip()
                if (response.isSuccessful) {
                    _ship.postValue(response.body()?.map { it.toShip() })
                    Log.d("check", "getShip: ok")
                } else {
                    _ship.postValue(emptyList())
                    Log.d("check", "getShip: fail1")
                }
            } catch (e: Exception) {
                _ship.postValue(emptyList())
                Log.d("check", "getShip: $e")
            }
        }
    }

    fun getShipAddressByAccount(account: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().shippingService.getShippingByAccount(account)
                Log.d("TAG", "getShip: $response")

                if (response.isSuccessful) {
                    _ship.postValue(response.body()?.map { it.toShip() })
                } else {
                    _ship.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "getShip: " + e.message)
                _ship.postValue(emptyList())
            }
        }
    }

    fun getShipAddressBySelect(select: Int,account:String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().shippingService.getShippingBySelect(select,account)
                Log.d("TAG", "getShipSl: $response")

                if (response.isSuccessful) {
                    _ship.postValue(response.body()?.map { it.toShip() })
                } else {
                    _ship.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "getShipSl: " + e.message)
                _ship.postValue(emptyList())
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