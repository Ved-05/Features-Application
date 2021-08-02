package com.esper.devices.domain.dto

data class EsperServiceResponse(
    val features: List<FeatureDTO>,
    val exclusions: List<List<ExclusionDTO>>,
)