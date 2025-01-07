package com.rakamin.mandirinews.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rakamin.mandirinews.data.News
import com.rakamin.mandirinews.data.NewsResponse
import com.rakamin.mandirinews.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    val headline = MutableLiveData<ArrayList<News>>()
    val everything = MutableLiveData<ArrayList<News>>()

    fun setHeadline() {
        RetrofitClient.instance.getHeadline().enqueue(object: Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if(response.body()?.articles != null) {
                    headline.postValue(response.body()?.articles)
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
            }
        })
    }

    fun setEverything(pageSize: Int) {
        RetrofitClient.instance.getEverything(pageSize).enqueue(object: Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if(response.body()?.articles != null) {
                    everything.postValue(response.body()?.articles)
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
            }
        })
    }

    fun getHeadline(): LiveData<ArrayList<News>> {
        return headline
    }

    fun getEverything(): LiveData<ArrayList<News>> {
        return everything
    }
}