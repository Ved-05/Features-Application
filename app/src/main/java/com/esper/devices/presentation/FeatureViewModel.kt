package com.esper.devices.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.esper.devices.domain.source.remote.RemoteSource
import com.esper.devices.domain.Repository
import com.esper.devices.presentation.data.Feature
import com.esper.devices.presentation.data.FeatureOption
import kotlinx.coroutines.launch

class FeatureViewModel() : ViewModel() {
    var mFeatures: MutableLiveData<Map<Int, Feature>> = MutableLiveData()
    private lateinit var mExclusions: Map<FeatureOption, FeatureOption>
    private var mExclusionForSelection = FeatureOption(-1, -1)
    private val mRepository: Repository = Repository(RemoteSource())

    init {
        viewModelScope.launch {
            mFeatures.postValue(mRepository.getFeatures())
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun updateListFromSelection(featureOption: FeatureOption, previousSelection: FeatureOption) {
        val currentFeatures: Map<Int, Feature>? = mFeatures.value
        mExclusions = mRepository.getExclusions()

        // update selection
        currentFeatures?.get(featureOption.featureId)?.options?.get(featureOption.optionId)?.isSelected = true
        currentFeatures?.get(previousSelection.featureId)?.options?.get(previousSelection.optionId)?.isSelected = false

        // get exclusion for selected option
        val defaultFeatureOption = FeatureOption(-1, -1)
        val exclusionForSelection = mExclusions.getOrDefault(featureOption, defaultFeatureOption)

        // mark any option as not allowed if exclusion exist
        if (!exclusionForSelection.featureId.equals(defaultFeatureOption)) {
            currentFeatures?.get(mExclusionForSelection.featureId)
                ?.options?.get(mExclusionForSelection.optionId)
                ?.isAllowed = true
            currentFeatures?.get(exclusionForSelection.featureId)
                ?.options?.get(exclusionForSelection.optionId)
                ?.isAllowed = false
            mExclusionForSelection = exclusionForSelection
        }

        // update the list
        mFeatures.value = currentFeatures
    }
}