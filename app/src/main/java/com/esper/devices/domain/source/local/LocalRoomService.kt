package com.esper.devices.domain.source.local

import androidx.room.Dao
import com.esper.devices.domain.source.remote.dto.EsperServiceResponse

@Dao
interface LocalRoomService {
    suspend fun listDB(): EsperServiceResponse
}