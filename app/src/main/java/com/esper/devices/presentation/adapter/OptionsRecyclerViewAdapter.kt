package com.esper.devices.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.esper.devices.R
import com.esper.devices.presentation.data.Feature
import com.esper.devices.presentation.data.FeatureOption
import com.esper.devices.presentation.data.Option

class OptionsRecyclerViewAdapter(
    feature: Feature,
    private var mOptionSelectedListener: OptionSelectedListener
) : RecyclerView.Adapter<OptionsRecyclerViewAdapter.OptionsViewHolder>() {
    private lateinit var mContext: Context
    private val mFeatureId = feature.featureId
    private val mOptions: List<Option> = feature.options.values.toList()
    private var mPreviousSelection = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        mContext = parent.context
        return OptionsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_option, parent, false))
    }

    override fun onBindViewHolder(optionsViewHolder: OptionsViewHolder, position: Int) {
        optionsViewHolder.bind(mOptions[position], mContext)

        // Set on complete listener on option view
        optionsViewHolder.optionView.setOnClickListener {
            val currentSelection = mOptions[position].optionId
            mOptionSelectedListener.onOptionSelectedListener(
                FeatureOption(mFeatureId, mOptions[position].optionId),
                FeatureOption(mFeatureId, mPreviousSelection)
            )
            mPreviousSelection = currentSelection
        }
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    /**
     * View Holder to hold options view and recycle them.
     */
    class OptionsViewHolder(optionView: View) : RecyclerView.ViewHolder(optionView) {
        private val optionTextView: TextView = optionView.findViewById(R.id.optionName)
        private val optionIcon: ImageView = optionView.findViewById(R.id.optionIcon)
        internal val optionView: LinearLayout = optionView.findViewById(R.id.optionView)

        /**
         * Bind Option data with UI.
         */
        fun bind(option: Option, context: Context) {
            // Bind option name
            optionTextView.text = option.name

            // Bind option icon (from url) as option icon
            Glide.with(context)
                .load(option.iconUrl)
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(optionIcon)

            // Bind background
            optionView.setBackgroundResource(
                if (option.isSelected) R.drawable.option_selected
                else if (!option.isAllowed) R.drawable.option_disabled
                else R.color.white
            )
            optionView.isEnabled = option.isAllowed
        }
    }

    interface OptionSelectedListener {
        fun onOptionSelectedListener(featureOption: FeatureOption, previousSelection: FeatureOption)
    }
}