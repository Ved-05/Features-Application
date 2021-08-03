package com.esper.devices.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esper.devices.databinding.ActivityFeaturesBinding
import com.esper.devices.domain.Repository
import com.esper.devices.domain.source.remote.RemoteSource
import com.esper.devices.mapper.Status
import com.esper.devices.presentation.adapter.FeaturesRecyclerViewAdapter
import com.esper.devices.presentation.adapter.OptionsRecyclerViewAdapter
import com.esper.devices.presentation.data.FeatureOption
import com.esper.devices.presentation.viewmodel.FeatureViewModel
import com.esper.devices.presentation.viewmodel.FeatureViewModelFactory

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

        // Loading state
        val progressBar = featuresViewBinder.progressBar
        val submitButton = featuresViewBinder.submitButton
        featuresViewModel.mDataOperationState.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    featuresRecyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    submitButton.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    featuresRecyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    submitButton.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    submitButton.visibility = View.GONE
                    featuresRecyclerView.visibility = View.GONE
                }
            }
        })

        // Submit button state
        featuresViewModel.mIsSelectionValid.observe(this, {
            submitButton.isEnabled = it
        })

        submitButton.setOnClickListener {
            navigateToSelectionDetailsActivity()
        }
    }

    /**
     * Navigates to details activity with valid selection
     */
    private fun navigateToSelectionDetailsActivity() {
        val toSelectionActivity = Intent(this, SelectionDetailsActivity::class.java)
        toSelectionActivity.putExtra(SELECTION, featuresViewModel.mSelection)
        startActivity(toSelectionActivity)
    }

    /**
     * Initialise feature view model
     */
    private fun initFeaturesViewModel() {
        val featureViewModelFactory = FeatureViewModelFactory(Repository(this.applicationContext, RemoteSource()))
        featuresViewModel = ViewModelProvider(this, featureViewModelFactory).get(FeatureViewModel::class.java)
    }

    private fun logEvent(logString: String) {
        Log.d(this.javaClass.simpleName, logString)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionSelectedListener(featureOption: FeatureOption, previousSelection: FeatureOption) {
        featuresViewModel.updateListFromSelection(featureOption, previousSelection)
        logEvent("Selected $featureOption. Previous selection was $previousSelection")
    }

    companion object {
        const val SELECTION = "SELECTION"
    }
}