package com.esper.devices.domain.source.remote

import com.esper.devices.domain.dto.EsperServiceResponse
import com.esper.devices.domain.source.DataSource

class RemoteSource : DataSource {
    override suspend fun getDB(): EsperServiceResponse {
        return RetrofitBuilder.esperService.listDB()
    }
}