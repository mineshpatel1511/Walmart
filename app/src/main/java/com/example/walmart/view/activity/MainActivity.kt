package com.example.walmart.view.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walmart.data.Country
import com.example.walmart.databinding.ActivityMainBinding
import com.example.walmart.view.adapter.CountryAdapter
import com.example.walmart.viewmodel.CountryViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var progressBar: View? = null
    private var emptyView: TextView? = null

    private var countryViewModel: CountryViewModel? = null
    private var countryAdapter: CountryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Progress Bar and Empty State.
        progressBar = binding.rootLayout.progressBar
        emptyView = binding.rootLayout.emptyView

        val countriesRecyclerView = binding.rootLayout.countriesRecyclerView
        countriesRecyclerView.layoutManager = LinearLayoutManager(this)
        countryAdapter = CountryAdapter()
        countriesRecyclerView.adapter = countryAdapter

        countryViewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        countryViewModel?.getCountryList()

        // Start a coroutine in the lifecycle scope
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countryViewModel?.uiState?.collect { uiState ->
                    when (uiState) {
                        is CountryViewModel.UiState.Success -> {
                            setCircularProgressBarVisibility(false)
                            setEmptyViewVisibility(false)
                            countryAdapter?.setList(uiState.data as MutableList<Country>?)
                            countryAdapter?.notifyDataSetChanged()
                        }
                        is CountryViewModel.UiState.Error -> {
                            setCircularProgressBarVisibility(false)
                            setEmptyViewVisibility(true)
                        }
                        else -> {
                            setCircularProgressBarVisibility(true)
                            setEmptyViewVisibility(false)
                        }
                    }
                }
            }
        }
    }

    private fun setCircularProgressBarVisibility(makeVisible: Boolean) {
        if (progressBar != null) {
            progressBar!!.visibility = if (makeVisible) View.VISIBLE else View.GONE
        }
    }

    private fun setEmptyViewVisibility(makeVisible: Boolean) {
        if (emptyView != null) {
            emptyView!!.visibility = if (makeVisible) View.VISIBLE else View.GONE
        }
    }
}
