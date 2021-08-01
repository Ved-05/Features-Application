package com.esper.devices.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.esper.devices.presentation.data.Feature
import com.esper.devices.presentation.data.FeatureOption
import com.esper.devices.presentation.data.Option

class FeatureViewModel : ViewModel() {
    lateinit var mFeatures: Map<Int, Feature>
    private lateinit var mExclusions: Map<FeatureOption, FeatureOption>
    private var mExclusionForSelection = FeatureOption(-1, -1)

    init {
        createDummyFeatures()
        createDummyExclusions()
    }

    private fun createDummyExclusions() {
        mExclusions = mutableMapOf(
            FeatureOption(1, 4) to FeatureOption(2, 6),
            FeatureOption(1, 3) to FeatureOption(3, 12),
            FeatureOption(2, 7) to FeatureOption(3, 12),
            FeatureOption(1, 4) to FeatureOption(3, 12),
        )
    }

    private fun createDummyFeatures() {
        mFeatures = mapOf(
            1 to Feature(
                1, "Mobile Phone",
                hashMapOf(
                    1 to Option(
                        1,
                        "Samsung S21",
                        "https://images.samsung.com/africa_en/smartphones/galaxy-s21/buy/galaxy-s21-phantom-violet.png"
                    ),
                    2 to Option(
                        2,
                        "One Plus 9",
                        "https://oasis.opstatics.com/content/dam/oasis/page/2021/9-series/spec-image/9/Astralblack_2080a-min_euna.png"
                    ),
                    3 to Option(
                        3,
                        "Pixel 5",
                        "https://images-na.ssl-images-amazon.com/images/I/81AqwYyZjzL._AC_SL1500_.jpg"
                    ),
                    4 to Option(
                        4,
                        "LG G8",
                        "https://www.gizmochina.com/wp-content/uploads/2019/08/Lg-G8-ThinQ-468x600.png"
                    ),
                ),
            ),
            2 to Feature(
                2, "Storage Options", hashMapOf(
                    6 to Option(
                        6,
                        "256 GB",
                        "https://image.shutterstock.com/image-vector/memory-card-256-gb-icon-600w-403452973.jpg"
                    ),
                    7 to Option(
                        7,
                        "128 GB",
                        "https://image.shutterstock.com/image-vector/memory-card-128-gb-icon-600w-403452997.jpg"
                    ),
                )
            ),
            3 to Feature(
                3, "Other features", hashMapOf(
                    10 to Option(
                        10,
                        "Wireless Charging",
                        "https://static.thenounproject.com/png/2039647-200.png"
                    ),
                    11 to Option(
                        11,
                        "Wide angle lens",
                        "https://cdn.iconscout.com/icon/premium/png-256-thumb/wide-angle-1946103-1645351.png"
                    ),
                    12 to Option(
                        12,
                        "Dual Sim",
                        "https://static.thenounproject.com/png/2277477-200.png"
                    )
                )
            ),
            4 to Feature(4, "Empty", emptyMap())
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun updateListFromSelection(featureOption: FeatureOption, previousSelection: FeatureOption) {
        // update selection
        mFeatures[featureOption.featureId]?.options?.get(featureOption.optionId)?.isSelected = true
        mFeatures[previousSelection.featureId]?.options?.get(previousSelection.optionId)?.isSelected = false

        // get exclusion
        val defaultFeatureOption = FeatureOption(-1, -1)
        val exclusionForSelection = mExclusions.getOrDefault(featureOption, defaultFeatureOption)

        // update list based on selection
        if (!exclusionForSelection.featureId.equals(defaultFeatureOption)) {
            mFeatures[mExclusionForSelection.featureId]?.options?.get(mExclusionForSelection.optionId)?.isAllowed = true
            mFeatures[exclusionForSelection.featureId]?.options?.get(exclusionForSelection.optionId)?.isAllowed = false
            mExclusionForSelection = exclusionForSelection
        }
    }
}