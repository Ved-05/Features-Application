package com.esper.devices.mapper

import com.esper.devices.domain.dto.ExclusionDTO
import com.esper.devices.domain.dto.FeatureDTO
import com.esper.devices.domain.dto.OptionDTO
import com.esper.devices.presentation.data.Feature
import com.esper.devices.presentation.data.FeatureOption
import com.esper.devices.presentation.data.Option

/**
 * Converts DTO to Presentation model
 */
object Mapper {
    /**
     * Maps FeatureDTO to Feature model
     */
    fun mapToFeatures(featureDTO: List<FeatureDTO>): Map<Int, Feature> {
        val features = mutableMapOf<Int, Feature>()
        featureDTO.forEach {
            val feature = Feature(it.feature_id.toInt(), it.name, mapToOptions(it.options))
            features[feature.featureId] = feature
        }
        return features
    }

    /**
     * Maps OptionDTO to map of Option model
     */
    private fun mapToOptions(optionsDTO: List<OptionDTO>): Map<Int, Option> {
        val options = mutableMapOf<Int, Option>()
        optionsDTO.forEach {
            val option = Option(it.id.toInt(), it.name, it.icon)
            options[option.optionId] = option
        }
        return options
    }

    /**
     * Maps ExclusionDTO to map of FeatureOption model
     */
    fun mapToExclusions(exclusionsDTO: List<List<ExclusionDTO>>): Map<FeatureOption, FeatureOption> {
        val exclusions = mutableMapOf<FeatureOption, FeatureOption>()
        exclusionsDTO.forEach {
            val featureOptions : List<FeatureOption> = getFeatureOption(it)
            exclusions[featureOptions[0]] = featureOptions[1]
        }
        return exclusions
    }

    /**
     * Converts ExclusionDTO to FeatureOptions
     */
    private fun getFeatureOption(entity: List<ExclusionDTO>): List<FeatureOption> {
        return listOf(
            FeatureOption(entity[0].feature_id.toInt(), entity[0].options_id.toInt()),
            FeatureOption(entity[1].feature_id.toInt(), entity[1].options_id.toInt())
        )
    }
}
