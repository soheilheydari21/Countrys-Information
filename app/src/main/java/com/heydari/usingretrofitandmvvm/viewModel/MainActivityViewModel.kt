package com.heydari.usingretrofitandmvvm.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.heydari.usingretrofitandmvvm.model.CountryModel
import com.heydari.usingretrofitandmvvm.service.RetroInstance
import com.heydari.usingretrofitandmvvm.service.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel: ViewModel() {
    var liveDataList: MutableLiveData<List<CountryModel>>

    init {
        liveDataList = MutableLiveData()
    }

    fun getLiveDataObserver(): MutableLiveData<List<CountryModel>> {
        return liveDataList
    }

    fun makeAPICall() {
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)
        val call = retroService.getCountryList()

        call.enqueue(object : Callback<List<CountryModel>> {
            override fun onFailure(call: Call<List<CountryModel>>, t: Throwable) {
                liveDataList.postValue(null)
            }
            override fun onResponse(call: Call<List<CountryModel>>, response: Response<List<CountryModel>>) {
                liveDataList.postValue(response.body())
            }
        })
    }

}