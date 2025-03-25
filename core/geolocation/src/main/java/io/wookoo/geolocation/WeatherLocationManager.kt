package io.wookoo.geolocation

import android.Manifest.permission
import android.content.Context
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import io.wookoo.common.ext.hasLocationPermissions
import io.wookoo.domain.repo.ILocationProvider
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// class WeatherLocationManager @Inject constructor(
//    @ApplicationContext private val context: Context,
// ) :
//    LocationListener, ILocationProvider {
//    private val locationManager =
//        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//
//    private var onSuccessfullyLocationReceived: ((latitude: Double, longitude: Double) -> Unit)? =
//        null
//    private var onError: ((AppError) -> Unit)? = null
//
//    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
//    override fun getGeolocationFromGpsSensors(
//        onSuccessfullyLocationReceived: (latitude: Double, longitude: Double) -> Unit,
//        onError: (AppError) -> Unit,
//    ) {
//        try {
//            if (!context.isFineLocationPermissionGranted()) return
//
//            this.onSuccessfullyLocationReceived = onSuccessfullyLocationReceived
//            this.onError = onError
//
//            val provider = when {
//                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
//                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
//                else -> null
//            }
//            if (provider == null) {
//                onError(DataError.Hardware.LOCATION_SERVICE_DISABLED)
//                return
//            }
//            locationManager.requestLocationUpdates(provider, 0L, 0f, this@WeatherLocationManager)
//        } catch (e: SecurityException) {
//            Log.e(TAG, "security", e)
//            onError(DataError.Hardware.UNKNOWN)
//        }
//    }
//
//    override fun onLocationChanged(location: Location) {
//        onSuccessfullyLocationReceived?.invoke(location.latitude, location.longitude)
//        locationManager.removeUpdates(this)
//    }
//
//    private companion object {
//        private const val TAG = "WeatherLocationManager"
//    }
// }

// class WeatherLocationManager @Inject constructor(
//    @ApplicationContext private val context: Context,
// ) : ILocationProvider {
//
//    private val locationManager =
//        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//
//    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
//    override fun getGeolocationFromGpsSensors(): Flow<AppResult<Pair<Double, Double>, DataError.Hardware>> =
//        callbackFlow {
//            if (!context.isFineLocationPermissionGranted()) {
//                trySend(AppResult.Error(DataError.Hardware.LOCATION_SERVICE_DISABLED))
//                close()
//                return@callbackFlow
//            }
//
//            val provider = when {
//                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
//                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
//                else -> null
//            }
//
//            if (provider == null) {
//                trySend(AppResult.Error(DataError.Hardware.LOCATION_SERVICE_DISABLED))
//                close()
//                return@callbackFlow
//            }
//
//            val locationListener = object : LocationListener {
//                override fun onLocationChanged(location: Location) {
//                    trySend(AppResult.Success(location.latitude to location.longitude))
//                }
//
//                override fun onProviderDisabled(provider: String) {
//                    trySend(AppResult.Error(DataError.Hardware.LOCATION_SERVICE_DISABLED))
//                }
//            }
//
//
//            locationManager.requestLocationUpdates(provider, 0L, 0f, locationListener)
//
//            awaitClose {
//                Log.d(TAG, "removed: ")
//                locationManager.removeUpdates(locationListener)
//            }
//
//
//        }
//    companion object{
//        private const val TAG = "WeatherLocationManager"
//    }
// }

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherLocationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val client: FusedLocationProviderClient,
) : ILocationProvider {

    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    override fun getGeolocationFromGpsSensors(): Flow<AppResult<Pair<Double, Double>, DataError.Hardware>> =
        callbackFlow {
            if (!context.hasLocationPermissions()) {
                trySend(AppResult.Error(DataError.Hardware.LOCATION_SERVICE_DISABLED))
                close()
                return@callbackFlow
            }
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                trySend(AppResult.Error(DataError.Hardware.LOCATION_SERVICE_DISABLED))
                close()
                return@callbackFlow
            }

            val request: LocationRequest =
                LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, INTERVAL).apply {
                    setMinUpdateDistanceMeters(100f)
                    setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                    setWaitForAccurateLocation(true)
                }.build()

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { location ->
                        Log.d(TAG, "$location: ")
                        launch { send(AppResult.Success(location.latitude to location.longitude)) }
                    }
                }
            }

            client.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )

            awaitClose {
                Log.d(TAG, "removed: ")
                client.removeLocationUpdates(locationCallback)
                client.flushLocations()
            }
        }

    companion object {
        private const val INTERVAL = 10000L
        private const val TAG = "WeatherLocationManager"
    }
}
