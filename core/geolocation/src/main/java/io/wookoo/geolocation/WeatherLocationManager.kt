package io.wookoo.geolocation

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import io.wookoo.common.isLocationPermissionGranted
import javax.inject.Inject

class WeatherLocationManager @Inject constructor(@ApplicationContext private val context: Context) :
    LocationListener {
    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private var onLocationReceived: ((latitude: Double, longitude: Double) -> Unit)? = null

    @SuppressLint("MissingPermission")
    fun getGeolocationFromGpsSensors(onLocationReceived: (latitude: Double, longitude: Double) -> Unit) {
        if (!isLocationPermissionGranted(context)) return
        this.onLocationReceived = onLocationReceived

        val provider = when {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
            else -> null
        }

        provider?.let {
            locationManager.requestLocationUpdates(it, 0L, 0f, this@WeatherLocationManager)
        }
    }

    override fun onLocationChanged(location: Location) {
        onLocationReceived?.invoke(location.latitude, location.longitude)
        locationManager.removeUpdates(this)
    }
}
