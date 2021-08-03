package com.esper.devices.domain

import android.content.Context
import com.esper.devices.domain.source.remote.dto.EsperServiceResponse
import com.esper.devices.domain.source.DataSource
import com.esper.devices.mapper.Mapper
import com.esper.devices.presentation.data.Feature
import com.esper.devices.presentation.data.FeatureOption

class Repository(private val context: Context, private val dataSource: DataSource) {
    private lateinit var response : EsperServiceResponse

    /**
     * Returns list of features & exclusions
     */
    suspend fun getFeatures(): Map<Int, Feature> {
        response = dataSource.getDB(context)
        println("$response")
        return Mapper.mapToFeatures(response.features)
    }

    fun getExclusions(): Map<FeatureOption, FeatureOption> {
        return Mapper.mapToExclusions(response.exclusions)
    }
}