package com.esper.devices.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esper.devices.domain.Repository
import com.esper.devices.mapper.Resource
import com.esper.devices.presentation.data.Feature
import com.esper.devices.presentation.data.FeatureOption
import com.esper.devices.presentation.data.Option
import kotlinx.coroutines.launch

class FeatureViewModel(private val mRepository: Repository) : ViewModel() {
    var mFeatures: MutableLiveData<Map<Int, Feature>> = MutableLiveData()
    private lateinit var mExclusions: Map<FeatureOption, FeatureOption>
    private var mExclusionForSelection = FeatureOption(-1, -1)
    var mDataOperationState: MutableLiveData<Resource> = MutableLiveData()
    var mIsSelectionValid: MutableLiveData<Boolean> = MutableLiveData()
    var mSelection : HashMap<String, Option> = HashMap()
    private var mFeatureSize : Int = 0

    init {
        loadDataFromSource()
        initSelection()
    }

    private fun initSelection() {
        mIsSelectionValid.value = false
    }

    private fun loadDataFromSource() {
        viewModelScope.launch {
            mDataOperationState.postValue(Resource.loading())
            try {
                mFeatures.postValue(mRepository.getFeatures())
                mDataOperationState.postValue(Resource.success())
            } catch (exception: Exception) {
                mDataOperationState.postValue(Resource.error("Problem loading data. Try connecting to internet."))
            }
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
        mFeatureSize = currentFeatures!!.keys.size
        updateSelection(featureOption, currentFeatures[featureOption.featureId])
    }

    /**
     * Update selection based on user input
     */
    private fun updateSelection(featureOption: FeatureOption, feature: Feature?) {
        mSelection[feature!!.name] = feature.options[featureOption.optionId]!!
        checkSelection()
    }

    /**
     * Check if the selection is valid/not
     */
    private fun checkSelection() {
        mIsSelectionValid.value = (mFeatureSize == mSelection.keys.size)
    }
}