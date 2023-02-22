package com.example.walmart.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.walmart.R
import com.example.walmart.data.Country
import com.example.walmart.view.adapter.CountryAdapter.CountryViewHolder

class CountryAdapter : RecyclerView.Adapter<CountryViewHolder>() {
    private var list: MutableList<Country>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bindData(list!![position])
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    class CountryViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val countryNameTextView: TextView
        private val countryCodeTextView: TextView
        private val countryCapitalTextView: TextView

        init {
            countryNameTextView = itemView.findViewById(R.id.countryNameTextView)
            countryCodeTextView = itemView.findViewById(R.id.countryCodeTextView)
            countryCapitalTextView = itemView.findViewById(R.id.countryCapitalTextView)
        }

        fun bindData(country: Country) {
            countryNameTextView.text = country.countryName + ", " + country.countryRegion
            countryCodeTextView.text = country.countryCode
            countryCapitalTextView.text = country.countryCapital
        }
    }

    fun getList(): MutableList<Country>? {
        return if (list != null) list!! else ArrayList()
    }

    fun setList(countryList: MutableList<Country>?) {
        if (list == null || list!!.isEmpty()) {
            list = countryList
        } else {
            list!!.addAll(countryList!!)
        }
    }
}