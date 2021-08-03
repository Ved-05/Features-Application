package com.esper.devices.domain.source.local

import android.content.Context
import com.esper.devices.domain.source.DataSource
import com.esper.devices.domain.source.remote.dto.EsperServiceResponse

class LocalSource : DataSource {
    override suspend fun getDB(context: Context): EsperServiceResponse {
        return EsperServiceResponse(emptyList(), emptyList())
    }
}