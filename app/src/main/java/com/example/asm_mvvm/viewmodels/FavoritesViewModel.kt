package com.example.asm_mvvm.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asm_mvvm.models.Favorites
import com.example.asm_mvvm.request.FavoritesRequest
import com.example.asm_mvvm.response.FavoritesResponse
import com.example.asm_mvvm.retrofit.RetrofitBase
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {

    private val _favorites = MutableLiveData<List<Favorites>>()
    val favorites: LiveData<List<Favorites>> = _favorites

    private val _favorite = MutableLiveData<FavoritesResponse>()
    val favorite: LiveData<FavoritesResponse> = _favorite

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFailed = MutableLiveData<Boolean>()
    val isFailed: LiveData<Boolean> get() = _isFailed

    fun getFavoritesByAccount(account: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().favoritesService.getFavoritesByAccount(account)
                if (response.isSuccessful) {
                    _favorites.postValue(response.body()?.map { it.toFavorites() })
                    _isFailed.value = false
                } else {
                    _favorites.postValue(emptyList())
                    _isFailed.value = true
                }
            } catch (e: Exception) {
                _favorites.postValue(emptyList())
                _isFailed.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getFavoritesByProductId(account:String,productId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().favoritesService.getFavoriteByProductId(account,productId)
                if (response.isSuccessful && response.body() != null) {
                    _favorite.value = response.body()
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

    fun addProductToFavorites(
        account: String,
        favoritesRequest: FavoritesRequest,
        successfulNotification: String,
        failureNotification: String,
        context: Context
    ) {
        viewModelScope.launch {
            favoritesRequest.id =null

            val response = RetrofitBase().favoritesService.addFavorites(favoritesRequest)
            if (response.isSuccessful) {
                getFavoritesByAccount(account)
                Toast.makeText(context, successfulNotification, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, failureNotification, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun searchFavorites(key: String,account:String) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().favoritesService.searchFavorites(key,account)
                if (response.isSuccessful) {
                    _favorites.postValue(response.body()?.map { it.toFavorites() })
                    Log.d("check", "searchFv: ok")
                } else {
                    _favorites.postValue(emptyList())
                    Log.d("check", "searchFv: fail1")
                }
            } catch (e: Exception) {
                _favorites.postValue(emptyList())
                Log.d("check", "searchFv: $e")
            }
        }
    }

    fun deleteFavorites(id: String,account: String,context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitBase().favoritesService.deleteFavorites(id)
                if (response.isSuccessful) {
                    getFavoritesByAccount(account)
                    Toast.makeText(context, "Hủy yêu thích thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Hủy yêu thích thất bại", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("TAG", "DeleteFv error: " + e.message)
            }
        }
    }

}