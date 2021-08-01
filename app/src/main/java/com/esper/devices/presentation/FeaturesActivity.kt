package com.esper.devices.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esper.devices.presentation.adapter.FeaturesRecyclerViewAdapter
import com.esper.devices.presentation.adapter.OptionsRecyclerViewAdapter
import com.esper.devices.presentation.data.FeatureOption
import com.esper.devices.databinding.ActivityFeaturesBinding

class FeaturesActivity : AppCompatActivity(), OptionsRecyclerViewAdapter.OptionSelectedListener {
    private lateinit var featuresViewBinder: ActivityFeaturesBinding
    private lateinit var featuresViewModel: FeatureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        featuresViewBinder = ActivityFeaturesBinding.inflate(layoutInflater)
        setContentView(featuresViewBinder.root)
        featuresViewModel = ViewModelProvider(this).get(FeatureViewModel::class.java)

        // init features recycler view
        val featuresRecyclerView = featuresViewBinder.featuresRecyclerView
        featuresRecyclerView.layoutManager = LinearLayoutManager(this)
        val featuresRecyclerViewAdapter = FeaturesRecyclerViewAdapter(this)
        featuresRecyclerViewAdapter.mFeatures = featuresViewModel.mFeatures.values.toList()
        featuresRecyclerViewAdapter.notifyDataSetChanged()
        featuresRecyclerView.adapter = featuresRecyclerViewAdapter
    }

    private fun logEvent(logString: String) {
        Log.d(this.javaClass.simpleName, logString)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionSelectedListener(featureOption: FeatureOption, previousSelection: FeatureOption) {
        featuresViewModel.updateListFromSelection(featureOption, previousSelection)
        logEvent("Selected $featureOption. Previous selection was $previousSelection")
        (featuresViewBinder.featuresRecyclerView.adapter
                as FeaturesRecyclerViewAdapter).mFeatures = featuresViewModel.mFeatures.values.toList()
        (featuresViewBinder.featuresRecyclerView.adapter
                as FeaturesRecyclerViewAdapter).notifyDataSetChanged()
    }
}