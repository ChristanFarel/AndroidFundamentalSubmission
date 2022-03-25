package com.example.sub1githubuser.ui.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sub1githubuser.ItemsItem
import com.example.sub1githubuser.SearchResponse
import com.example.sub1githubuser.database.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _array = MutableLiveData<ArrayList<ItemsItem>>()
    val listUser: LiveData<ArrayList<ItemsItem>> = _array

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    private val _showError = MutableLiveData<String>()
    val showEror: LiveData<String> = _showError

    init {
        findUser("")
    }

    fun findUser(cariUser: String) {
        _showLoading.value = true
        val client = ApiConfig.getApiService().getUser(cariUser)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _showLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.items.isEmpty()) {
                            _showError.value = "User tidak ditemukan!"
                        } else {
                            _showError.value = ""
                        }
                        _array.value = responseBody.items
                    } else {
                        _showError.value = "Data tidak ditemukan silahkan cari!"
                    }
                } else {
                    _showError.value = "Data tidak ditemukan silahkan cari!"
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _showError.value = "Internet Error"
                _showLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

}