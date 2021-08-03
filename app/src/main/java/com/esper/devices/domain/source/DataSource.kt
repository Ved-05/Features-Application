package com.esper.devices.domain.source

import android.content.Context
import com.esper.devices.domain.source.remote.dto.EsperServiceResponse

interface DataSource {
    suspend fun getDB(context: Context) : EsperServiceResponse
}