package io.wookoo.weatherappportfolio

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import dagger.hilt.android.AndroidEntryPoint
import io.wookoo.common.isLocationPermissionGranted
import io.wookoo.common.openAndroidSettings
import io.wookoo.designsystem.ui.components.SharedLottieLoader
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.geolocation.WeatherLocationManager
import io.wookoo.permissions.PermissionDialog
import io.wookoo.permissions.Permissions
import io.wookoo.weatherappportfolio.navigation.Navigation
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var locationManager: WeatherLocationManager

    @Inject
    lateinit var permissions: Permissions

    @Inject
    lateinit var dataStore: IDataStoreRepo

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val userSettings by dataStore.userSettings.collectAsState(initial = null)

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
                if (isLocationPermissionGranted(this)) {
                    permissions.dismissDialog()
                }
            }

            WeatherAppPortfolioTheme {
                permissions.visiblePermissionDialogQueue
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

                Scaffold(
                    snackbarHost = {
                        val snackBarHostState = remember { SnackbarHostState() }
                        SnackbarHost(
                            modifier = Modifier.fillMaxWidth(),
                            hostState = snackBarHostState,
                            snackbar = { snackBarData ->
                                Snackbar {
                                }
                            }
                        )
                    }
                ) {
                    Crossfade(
                        targetState = when {
                            userSettings?.isLocationChoose == null -> io.wookoo.designsystem.ui.Crossfade.LOADING
                            else -> io.wookoo.designsystem.ui.Crossfade.CONTENT
                        },
                        label = ""
                    ) { screenState ->
                        when (screenState) {
                            io.wookoo.designsystem.ui.Crossfade.LOADING -> SharedLottieLoader()
                            io.wookoo.designsystem.ui.Crossfade.CONTENT -> {
                                Navigation(
                                    onRequestLocationPermission = {
                                        locationPermissionResultLauncher.launch(
                                            Manifest.permission.ACCESS_FINE_LOCATION
                                        )
                                    },
                                    userSettings = userSettings,
                                )
                            }

                            else -> Unit
                        }
                    }
                }
            }
        }
    }
}
