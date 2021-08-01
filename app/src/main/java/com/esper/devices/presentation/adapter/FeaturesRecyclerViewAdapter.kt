package com.esper.devices.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esper.devices.R
import com.esper.devices.presentation.data.Feature

class FeaturesRecyclerViewAdapter(
    private val mOptionSelectedListener: OptionsRecyclerViewAdapter.OptionSelectedListener
) : RecyclerView.Adapter<FeaturesRecyclerViewAdapter.FeaturesViewHolder>() {
    internal lateinit var mFeatures: List<Feature>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturesViewHolder {
        return FeaturesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_feature, parent, false))
    }

    override fun onBindViewHolder(featuresViewHolder: FeaturesViewHolder, position: Int) {
        featuresViewHolder.bind(mFeatures[position], mOptionSelectedListener)
    }

    override fun getItemCount(): Int {
        return mFeatures.size
    }

    /**
     * View Holder to hold features view and recycle them.
     */
    class FeaturesViewHolder(featureView: View) : RecyclerView.ViewHolder(featureView) {
        private val featureTextView: TextView = featureView.findViewById(R.id.featureName)
        private val optionsRecyclerView: RecyclerView = featureView.findViewById(R.id.optionsRecyclerView)
        private lateinit var optionsRecyclerViewAdapter: OptionsRecyclerViewAdapter
        /**
         * Bind feature data with UI.
         */
        fun bind(feature: Feature, mOptionSelectedListener: OptionsRecyclerViewAdapter.OptionSelectedListener) {
            // Bind feature name
            featureTextView.text = feature.name

            // Bind feature options to recycler view
            optionsRecyclerViewAdapter = OptionsRecyclerViewAdapter(feature, mOptionSelectedListener)
            optionsRecyclerView.adapter = optionsRecyclerViewAdapter
        }
    }
}