package com.yazazzello.adyen

import android.Manifest
import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yazazzello.adyen.databinding.BottomSheetBinding
import com.yazazzello.adyen.di.Params
import com.yazazzello.adyen.features.foursquare.FoursquareContract
import com.yazazzello.adyen.features.foursquare.VenueButtonViewModel
import com.yazazzello.adyen.features.foursquare.VenueListener
import com.yazazzello.adyen.restapi.models.VenuesResponses
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.main_content.*
import org.koin.android.ext.android.inject


class MapsActivity : FragmentActivity(), OnMapReadyCallback, FoursquareContract.View {

    override val presenter: FoursquareContract.Presenter by inject { mapOf(Params.FOURSQUARE_VIEW to this) }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentCategoryId: String
    private val rxPermissions = RxPermissions(this)
    private var googleMap: GoogleMap? = null
    private var bottomSheetBinding: BottomSheetBinding? = null
    private var currentLocation: Location? = null

    private val venueTypesList = listOf(
        VenueButtonViewModel("Pizza Places", "4bf58dd8d48988d1ca941735"),
        VenueButtonViewModel("Cafes", "4bf58dd8d48988d16d941735"),
        VenueButtonViewModel("Burgers", "4bf58dd8d48988d16c941735")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        (map as SupportMapFragment).getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        initBottomSheet()
    }

    @SuppressLint("CheckResult", "MissingPermission")
    private fun requestPermissions() {
        if (!rxPermissions.isGranted(Manifest.permission.ACCESS_FINE_LOCATION) ||
            !rxPermissions.isGranted(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            rxPermissions.request(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
                .subscribe({ granted ->
                    if (granted) {
                        requestLocation()
                    } else {
                        showError("Permission is not granted")
                    }
                }, {
                    showError(it.localizedMessage)
                })
        } else {
            requestLocation()
        }

    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        googleMap?.isMyLocationEnabled = true
        fusedLocationClient
            .lastLocation
            .addOnSuccessListener { location ->
                currentLocation = location
                presenter.loadObjects(location, currentCategoryId)
            }
    }

    private fun initBottomSheet() {
        bottomSheetBinding = DataBindingUtil.bind(bottom_sheet)
        bottomSheetBinding?.run {
            venueListener = object : VenueListener {
                override fun onVenueSelected(venueId: String) {
                    BottomSheetBehavior.from(bottom_sheet).state = BottomSheetBehavior.STATE_COLLAPSED
                    currentCategoryId = venueId
                    bottomSheetBinding?.venue = null
                    bottomSheetBinding?.photoUrl = null
                    currentLocation?.let {
                        presenter.loadObjects(it, currentCategoryId)
                    }
                }
            }
            venueTypes = venueTypesList
        }
    }

    override fun onStop() {
        presenter.stop()
        super.onStop()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        requestPermissions()
    }

    override fun flipProgress(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun displayVenues(list: List<VenuesResponses.Response.Venue>) {
        googleMap?.let { map ->
            map.clear()
            val boundsBuilder = LatLngBounds.Builder()
            list.forEach {
                val markerOptions = MarkerOptions()
                    .position(
                        LatLng(
                            it.location.lat ?: 0.toDouble(),
                            it.location.lng ?: 0.toDouble()
                        )
                    )
                    .title(it.name)
                val marker = map.addMarker(markerOptions)
                marker?.tag = it
                map.setOnMarkerClickListener {
                    val venue = it.tag as VenuesResponses.Response.Venue
                    bottomSheetBinding?.venue = venue
                    presenter.loadPhoto(venue.id)
                    false
                }
                map.setOnInfoWindowClickListener {
                    BottomSheetBehavior.from(bottom_sheet).state =
                            BottomSheetBehavior.STATE_EXPANDED

                }
                boundsBuilder.include(markerOptions.position)
            }
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 50))
        }
    }

    override fun onPhotoUrlLoaded(photoUrl: String) {
        bottomSheetBinding?.photoUrl = photoUrl
    }

    override fun showError(error: String) {
        Snackbar.make(coordinator, error, 2000).show()
    }
}
