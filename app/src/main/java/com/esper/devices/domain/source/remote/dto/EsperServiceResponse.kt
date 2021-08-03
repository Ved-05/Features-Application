package com.esper.devices.domain.source.remote.dto

data class EsperServiceResponse(
    val features: List<FeatureDTO>,
    val exclusions: List<List<ExclusionDTO>>,
)