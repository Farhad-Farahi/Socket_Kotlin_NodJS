package com.hexfa.map.presentation.ui.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.hexfa.map.R
import com.hexfa.map.databinding.ActivityMapBinding
import com.hexfa.map.domain.common.NetworkResult
import com.hexfa.map.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.hexfa.map.domain.models.Poi
import com.hexfa.map.presentation.ui.fragment.BottomSheetFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private val viewModel by viewModels<MainViewModel>()
    private val mapTag = "MapActivity_Log"
    private lateinit var value: String
    private lateinit var markers: ArrayList<Marker>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        value = intent.getStringExtra("value").toString()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        fetchData()

        mMap.setOnMarkerClickListener(this)

    }

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.taxis.collectLatest { response ->
                    when (response) {
                        is NetworkResult.Success -> {
                            hideProgressBar()
                            response.data?.let { taxisResponse ->
                                taxisResponse.poiList.forEach {
                                    drawMarker(it)
                                }
                            }
                        }
                        is NetworkResult.Error -> {
                            hideProgressBar()
                            response.message?.let { message ->
                                Log.e(mapTag, "An error occurred: $message")
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

    private fun drawMarker(it: Poi) {
        val lat = it.coordinate.latitude
        val lng = it.coordinate.longitude
        val type = it.fleetType
        val heading = it.heading
        val position = LatLng(lat, lng)

        //Create small Marker for Taxi
        val height = 100
        val width = 100
        val bitmapTaxi = BitmapFactory.decodeResource(resources, R.drawable.taxi)
        val smallBitmapTaxi = Bitmap.createScaledBitmap(bitmapTaxi, width, height, false)
        val smallerMarkerIconTaxi = BitmapDescriptorFactory.fromBitmap(smallBitmapTaxi)

        //Create small Marker for Pooling
        val bitmapPooling = BitmapFactory.decodeResource(resources, R.drawable.pooling)
        val smallBitmapPooling =
            Bitmap.createScaledBitmap(bitmapPooling, width + 100, height + 100, false)
        val smallerMarkerIconPooling = BitmapDescriptorFactory.fromBitmap(smallBitmapPooling)

        markers = ArrayList()
        if (type == "TAXI") {
            mMap.addMarker(
                MarkerOptions()
                    .position(position)
                    .title(type)
                    .icon(smallerMarkerIconTaxi)
                    .rotation(heading.toFloat())
            ).also {
                markers.add(it!!)
            }

        } else {
            mMap.addMarker(
                MarkerOptions()
                    .position(position)
                    .title(type)
                    .icon(smallerMarkerIconPooling)
                    .rotation(heading.toFloat())
            ).also {
                markers.add(it!!)
            }
        }
        for (marker in markers) {
            if (marker.position.toString() == value) { //if a marker has desired tag
                marker.showInfoWindow()
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 17f), null)
                openBottomSheetRequest()
            }
        }

    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        marker.showInfoWindow()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 17f), null)
        openBottomSheetRequest()

        return true
    }

    private fun openBottomSheetRequest() {
        val bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialogTag")
    }
}