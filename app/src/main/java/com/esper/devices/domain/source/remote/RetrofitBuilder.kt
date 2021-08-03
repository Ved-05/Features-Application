package com.esper.devices.domain.source.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://my-json-server.typicode.com/mhrpatel12/esper-assignment/"

    private fun getRetrofit(context: Context): Retrofit {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                val request = chain.request()
                if (!hasNetwork(context))
                    request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=${60 * 60 * 24 * 7}")
                        .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    /**
     * Returns esper service response
     */
    fun getService(context: Context): EsperService {
        return getRetrofit(context).create(EsperService::class.java)
    }

    /**
     * Returns the state of network.
     */
    private fun hasNetwork(context: Context): Boolean {
        var isConnected = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected) {
            isConnected = when (activeNetwork.type) {
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                ConnectivityManager.TYPE_ETHERNET -> true
                else -> false
            }
        }
        return isConnected
    }
}