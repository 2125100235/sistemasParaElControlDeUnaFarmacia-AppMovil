package com.logimedtech.farmacia.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.0.102/xampp/htdocs/sistemasParaElControlDeUnaFarmacia-Actualizado/php/"
    // by lazy garantiza que retrofit se cree solo por primera vez que la app lo necesita
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    }
