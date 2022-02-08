package com.hexfa.map.presentation.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.hexfa.map.databinding.ActivityMainBinding
import com.hexfa.map.domain.common.NetworkResult
import com.hexfa.map.presentation.adapter.TaxiAdapter
import com.hexfa.map.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val viewModel: MainViewModel by viewModels()
    private lateinit var taxisAdapter: TaxiAdapter
    private lateinit var binding: ActivityMainBinding
    private val mainTag = "MainActivity_Log"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //prevent the night mode of Android Operating System to Disarrange The User Interface
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setUpRecyclerView()
        taxisObserver()
        swipeRefreshLayoutFunctionality()
        onClickEvents()
    }

    private fun onClickEvents() {
        binding.fbMap.setOnClickListener {
            Intent(this , MapActivity::class.java).also { startActivity(it) }
        }
        taxisAdapter.setOnRequestButtonClickListener { taxi ->
            showSnackBar("Request to car : ${taxi.id}")
        }
        taxisAdapter.setonItemClickListener { taxi ->
            val value: String = LatLng(taxi.coordinate.latitude , taxi.coordinate.longitude).toString() // or just your string
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("value", value)
            startActivity(intent)
        }
    }

    private fun swipeRefreshLayoutFunctionality() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            manualRefresh()
        }
    }

    private fun taxisObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.taxis.collectLatest {response ->
                    when (response) {
                        is NetworkResult.Success -> {
                            hideProgressBar()
                            response.data?.let { taxisResponse ->
                                taxisAdapter.differ.submitList(taxisResponse.poiList)
                            }
                        }
                        is NetworkResult.Error -> {
                            hideProgressBar()
                            response.message?.let { message ->
                                Log.e(mainTag, "An error occurred: $message")
                            }
                        }
                        is NetworkResult.Loading -> {
                            showProgressBar()
                        }
                    }
                }
            }
        }
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun manualRefresh() {
        viewModel.onManualRefresh()
        showSnackBar("Data Refreshed")
    }

    private fun showSnackBar(text : String) {
        val snackBar = Snackbar.make(
            findViewById(android.R.id.content),
            text,
            Snackbar.LENGTH_LONG
        )

        snackBar.setAction("OK") { // Call your action method here
            snackBar.dismiss()
        }
        snackBar.show()
    }

    private fun setUpRecyclerView(){
        taxisAdapter = TaxiAdapter()
        binding.apply {
            taxisRecyclerView.adapter = taxisAdapter
            taxisRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}
