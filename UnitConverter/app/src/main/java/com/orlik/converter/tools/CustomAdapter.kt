package com.orlik.converter.tools

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import com.orlik.converter.R

class CustomAdapter(context: Context, private val items: Array<String>)
    : ArrayAdapter<String>(context, R.layout.dropdown_item, items) {

    private val noOpFilter = object : Filter() {
        private val noOpResult = FilterResults()
        override fun performFiltering(constraint: CharSequence?) = noOpResult
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {}
    }

    override fun getFilter() = noOpFilter
}