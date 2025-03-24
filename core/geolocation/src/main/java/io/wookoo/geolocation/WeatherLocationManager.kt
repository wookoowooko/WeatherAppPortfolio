package io.wookoo.geolocation

import android.Manifest.permission
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import io.wookoo.common.ext.isFineLocationPermissionGranted
import io.wookoo.domain.repo.ILocationProvider
import io.wookoo.domain.utils.AppError
import io.wookoo.domain.utils.DataError
import javax.inject.Inject

class WeatherLocationManager @Inject constructor(
    @ApplicationContext private val context: Context,
) :
    LocationListener, ILocationProvider {
    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private var onSuccessfullyLocationReceived: ((latitude: Double, longitude: Double) -> Unit)? =
        null
    private var onError: ((AppError) -> Unit)? = null

    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    override fun getGeolocationFromGpsSensors(
        onSuccessfullyLocationReceived: (latitude: Double, longitude: Double) -> Unit,
        onError: (AppError) -> Unit,
    ) {
        try {
            if (!context.isFineLocationPermissionGranted()) return

            this.onSuccessfullyLocationReceived = onSuccessfullyLocationReceived
            this.onError = onError

            val provider = when {
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
                else -> null
            }
            if (provider == null) {
                onError(DataError.Hardware.LOCATION_SERVICE_DISABLED)
                return
            }
            locationManager.requestLocationUpdates(provider, 0L, 0f, this@WeatherLocationManager)


        } catch (e: SecurityException) {
            Log.e(TAG, "security", e)
            onError(DataError.Hardware.UNKNOWN)
        } catch (e: Exception) {
            Log.e(TAG, "exe", e)
            onError(DataError.Hardware.LOCATION_SERVICE_DISABLED)
        }

    }

    override fun onLocationChanged(location: Location) {
        onSuccessfullyLocationReceived?.invoke(location.latitude, location.longitude)
        locationManager.removeUpdates(this)
    }

    private companion object {
        private const val TAG = "WeatherLocationManager"
    }
}
