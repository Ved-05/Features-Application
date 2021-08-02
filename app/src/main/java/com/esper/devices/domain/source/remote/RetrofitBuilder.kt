package com.esper.devices.domain.source.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://my-json-server.typicode.com/mhrpatel12/esper-assignment/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val esperService: EsperService = getRetrofit().create(EsperService::class.java)
}