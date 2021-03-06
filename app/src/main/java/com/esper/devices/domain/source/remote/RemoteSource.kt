package com.esper.devices.domain.source.remote

import android.content.Context
import com.esper.devices.domain.source.DataSource
import com.esper.devices.domain.source.remote.dto.EsperServiceResponse

class RemoteSource : DataSource {
    override suspend fun getDB(context: Context): EsperServiceResponse {
        return RetrofitBuilder.getService(context).listDB()
    }
}