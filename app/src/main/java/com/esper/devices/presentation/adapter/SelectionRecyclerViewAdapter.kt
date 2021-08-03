package com.esper.devices.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.esper.devices.R
import com.esper.devices.presentation.data.Option

class SelectionRecyclerViewAdapter(
    private val mSelections: Array<Pair<String, Option>>
) : RecyclerView.Adapter<SelectionRecyclerViewAdapter.SelectionViewHolder>() {
    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        mContext = parent.context
        return SelectionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_selection, parent, false)
        )
    }

    override fun onBindViewHolder(featuresViewHolder: SelectionViewHolder, position: Int) {
        featuresViewHolder.bind(mSelections[position], mContext)
    }

    override fun getItemCount(): Int {
        return mSelections.size
    }

    /**
     * View Holder to hold features view and recycle them.
     */
    class SelectionViewHolder(selectionView: View) : RecyclerView.ViewHolder(selectionView) {
        private val selectionTitle: TextView = selectionView.findViewById(R.id.selectionTitle)
        private val selectionImage: ImageView = selectionView.findViewById(R.id.optionIcon)
        private val selectionName: TextView = selectionView.findViewById(R.id.optionName)

        /**
         * Bind feature data with UI.
         */
        fun bind(selection: Pair<String, Option>, context: Context) {
            // Bind selection name
            selectionTitle.text = selection.first

            // Bind option
            // Bind option icon (from url) as option icon
            Glide.with(context)
                .load(selection.second.iconUrl)
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(selectionImage)
            selectionName.text = selection.second.name
        }
    }
}