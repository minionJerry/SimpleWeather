package com.kanykeinu.simpleweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.kanykeinu.simpleweather.view.WeatherInfoFragment

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnMarkerClickListener {

    companion object {
        const val OFFICE_LAT = 52.364138
        const val OFFICE_LNG = 4.891697
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment: SupportMapFragment? =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        p0 ?: return
        val officeLocation = LatLng(OFFICE_LAT, OFFICE_LNG)
        with(p0) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(officeLocation, 15f))
            addMarker(
                MarkerOptions()
                    .position(officeLocation)
                    .title("Siilo Office")
            )
                .showInfoWindow()
            setOnMapClickListener(this@MainActivity)
            setOnMarkerClickListener(this@MainActivity)
        }
        openDialog(officeLocation)
    }

    override fun onMapClick(p0: LatLng?) {
        p0?.let { openDialog(it) }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        p0?.showInfoWindow()
        p0?.position?.let { openDialog(it) }
        return true
    }

    private fun openDialog(latLng: LatLng) {
        WeatherInfoFragment
            .newInstance(lat = latLng.latitude, lng = latLng.longitude)
            .show(supportFragmentManager, WeatherInfoFragment.TAG)

    }
}
