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
        // setup features view model
        initFeaturesViewModel()
        // setup data observers
        setupDataObservers()
    }

    private fun setupDataObservers() {
        // Set recycler view to observe on features list
        val featuresRecyclerView = featuresViewBinder.featuresRecyclerView
        featuresRecyclerView.layoutManager = LinearLayoutManager(this)
        val featuresRecyclerViewAdapter = FeaturesRecyclerViewAdapter(this)
        featuresViewModel.mFeatures.observe(this, {
            featuresRecyclerViewAdapter.mFeatures = it.values.toList()
            featuresRecyclerViewAdapter.notifyDataSetChanged()
        })
        featuresRecyclerView.adapter = featuresRecyclerViewAdapter
    }

    private fun initFeaturesViewModel() {
        featuresViewModel = ViewModelProvider(this).get(FeatureViewModel::class.java)
    }

    private fun logEvent(logString: String) {
        Log.d(this.javaClass.simpleName, logString)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionSelectedListener(featureOption: FeatureOption, previousSelection: FeatureOption) {
        featuresViewModel.updateListFromSelection(featureOption, previousSelection)
        logEvent("Selected $featureOption. Previous selection was $previousSelection")
    }
}