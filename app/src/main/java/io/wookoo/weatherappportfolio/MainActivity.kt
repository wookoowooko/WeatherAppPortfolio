package io.wookoo.weatherappportfolio

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import io.wookoo.common.ext.isFineLocationPermissionGranted
import io.wookoo.common.ext.openAndroidSettings
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.service.IConnectivityObserver
import io.wookoo.geolocation.WeatherLocationManager
import io.wookoo.permissions.PermissionDialog
import io.wookoo.permissions.Permissions
import io.wookoo.weatherappportfolio.appstate.rememberAppState
import io.wookoo.weatherappportfolio.composeapp.WeatherApp
import io.wookoo.worker.SyncWeather
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var locationManager: WeatherLocationManager

    @Inject
    lateinit var permissions: Permissions

    @Inject
    lateinit var dataStore: IDataStoreRepo

    @Inject
    lateinit var connectivityObserver: IConnectivityObserver

    @Inject
    lateinit var repo: IMasterWeatherRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                repo.getCurrentWeatherIds().collect { listOfIds ->
//                    if (listOfIds.isNotEmpty()) {
//                        SyncWeather.initialize(this@MainActivity.applicationContext)
//                    }
//                }
//            }
//        }

        enableEdgeToEdge()
        setContent {
            val appState = rememberAppState(
                networkMonitor = connectivityObserver,
                dataStore = dataStore,
            )
            val locationPermissionResultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    permissions.onPermissionResult(
                        permission = Manifest.permission.ACCESS_FINE_LOCATION,
                        isGranted = isGranted
                    )
                }
            )

            LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
                if (this.isFineLocationPermissionGranted()) {
                    permissions.dismissDialog()
                }
            }

            WeatherAppPortfolioTheme {
                permissions
                    .visiblePermissionDialogQueue
                    .reversed()
                    .forEach { permission ->
                        PermissionDialog(
                            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                                permission
                            ),
                            onDismiss = permissions::dismissDialog,
                            onOkClick = {
                                permissions.dismissDialog()
                                locationPermissionResultLauncher.launch(permission)
                            },
                            onGoToAppSettingsClick = ::openAndroidSettings,
                        )
                    }

                WeatherApp(appState, onRequestLocationPermission = {
                    locationPermissionResultLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                })
            }
        }
    }
}
