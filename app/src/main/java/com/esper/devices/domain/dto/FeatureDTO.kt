package com.esper.devices.domain.dto

data class FeatureDTO(
    val feature_id: String,
    val name: String,
    val options: List<OptionDTO>
)