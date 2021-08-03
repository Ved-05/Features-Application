package com.esper.devices.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.esper.devices.R
import com.esper.devices.presentation.data.Option
import com.esper.devices.presentation.adapter.SelectionRecyclerViewAdapter

class SelectionDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_details)
        supportActionBar?.title = "Selection"
        initUI()
    }

    /**
     * Initialise UI with data
     */
    private fun initUI() {
        val selections = intent.extras?.getSerializable(FeaturesActivity.SELECTION) as HashMap<String, Option>
        val validSelections: Array<Pair<String, Option>> = selections.toList().toTypedArray()
        val selectionsRecyclerView = findViewById<RecyclerView>(R.id.selectionRecyclerView)
        val selectionRecyclerViewAdapter = SelectionRecyclerViewAdapter(validSelections)
        selectionsRecyclerView.adapter = selectionRecyclerViewAdapter
    }
}