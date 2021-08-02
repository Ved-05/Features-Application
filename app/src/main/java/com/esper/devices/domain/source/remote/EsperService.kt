package com.esper.devices.domain.source.remote

import com.esper.devices.domain.dto.EsperServiceResponse
import retrofit2.http.GET

/**
 * Esper service interface that creates an API to fetch data from remote server
 */
interface EsperService {
    @GET("db")
    suspend fun listDB(): EsperServiceResponse
}