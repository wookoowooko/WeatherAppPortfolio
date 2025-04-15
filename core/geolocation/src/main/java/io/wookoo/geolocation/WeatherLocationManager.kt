/*
 * Copyright 2025  - Ruslan Gaivoronskii (aka wookoowookoo) https://github.com/wookoowooko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.wookoo.geolocation

import android.Manifest.permission
import android.content.Context
import android.location.LocationManager
import android.os.Looper
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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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
                client.removeLocationUpdates(locationCallback)
                client.flushLocations()
            }
        }

    companion object {
        private const val INTERVAL = 10000L
    }
}
