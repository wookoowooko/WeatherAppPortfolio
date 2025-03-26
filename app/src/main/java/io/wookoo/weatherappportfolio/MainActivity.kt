package io.wookoo.weatherappportfolio

import android.Manifest
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import dagger.hilt.android.AndroidEntryPoint
import io.wookoo.common.ext.hasLocationPermissions
import io.wookoo.common.ext.openAndroidSettings
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.service.IConnectivityObserver
import io.wookoo.geolocation.WeatherLocationManager
import io.wookoo.permissions.PermissionDialog
import io.wookoo.permissions.Permissions
import io.wookoo.weatherappportfolio.appstate.rememberAppState
import io.wookoo.weatherappportfolio.composeapp.WeatherApp
import io.wookoo.worker.utils.Sync
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var locationManager: WeatherLocationManager

    @Inject
    lateinit var permissions: Permissions

    @Inject
    lateinit var dataStore: IDataStoreRepo

    @Inject
    lateinit var connectivityObserver: IConnectivityObserver

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val locales = newConfig.locales
        Log.d(TAG, "onConfigurationChanged: $locales")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.splashState.value.shouldKeepSplashScreen()
        }

        enableEdgeToEdge()

        setContent {
            val startScreenState by splashViewModel.splashState.collectAsState()
            val startDestination = startScreenState.startDestination()
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
                if (this.hasLocationPermissions()) {
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

                if (startDestination != null) {
                    WeatherApp(
                        startDestination = startDestination,
                        appState = appState,
                        onRequestLocationPermission = {
                            locationPermissionResultLauncher.launch(
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        },
                        onSyncRequest = { geoItemId, isNeedToUpdate ->
                            Sync.initializeOneTime(
                                context = this@MainActivity,
                                locationId = geoItemId,
                                isNeedToUpdate = isNeedToUpdate
                            )
                        }
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
