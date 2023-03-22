package com.x7.kotlin_caronavirusapp.network

import com.x7.kotlin_caronavirusapp.model.Carona
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

   //https://disease.sh/v3/covid-19/countries/

    @GET("countries")
    fun getAllCountries():Call<ArrayList<Carona>>

    @GET("countries/{name}")
    fun getCountryData(@Path("name") name:String):Call<Carona>

}