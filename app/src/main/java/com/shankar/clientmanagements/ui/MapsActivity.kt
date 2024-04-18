package com.shankar.clientmanagements.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.shankar.clientmanagements.R
import com.shankar.clientmanagements.entity.LatitudeLogitude

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var lstLatitudeLogitude=ArrayList<LatitudeLogitude>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        /* mMap.addMarker(
             MarkerOptions().position(LatLng(27.7061355, 85.3294626))
                 .title("Hamro College")
                 .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

         )
         mMap.animateCamera(
             CameraUpdateFactory.newLatLngZoom(LatLng(27.7061355,85.3294626),14F), 3000,null
         )
         mMap.uiSettings.isZoomControlsEnabled=true
         mMap.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom=true
 */     lstLatitudeLogitude.add(LatitudeLogitude(27.706895, 85.340863, "Ram Mandir"))
        lstLatitudeLogitude.add(LatitudeLogitude(27.6980133, 85.3199651, "Ram Mandir"))

        for(location in lstLatitudeLogitude){
            mMap.addMarker(
                MarkerOptions().position(LatLng(location.latitude,location.logitude))
                    .title(location.markerName)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(27.7061355,85.3294626),14F), 3000,null
        )
        mMap.uiSettings.isZoomControlsEnabled=true
        mMap.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom=true
        mMap.uiSettings.isMyLocationButtonEnabled=true
        mMap.uiSettings.isCompassEnabled= true


    }
}