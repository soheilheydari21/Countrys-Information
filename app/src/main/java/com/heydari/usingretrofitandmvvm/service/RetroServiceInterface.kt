package com.heydari.usingretrofitandmvvm.service

import com.heydari.usingretrofitandmvvm.model.CountryModel
import retrofit2.Call
import retrofit2.http.GET

interface RetroServiceInterface {

    @GET("countries")
    fun getCountryList(): Call<List<CountryModel>>
}