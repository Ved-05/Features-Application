package com.esper.devices.domain.source

import com.esper.devices.domain.dto.EsperServiceResponse

interface DataSource {
    suspend fun getDB() : EsperServiceResponse
}