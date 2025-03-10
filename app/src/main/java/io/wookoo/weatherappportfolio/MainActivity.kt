package io.wookoo.weatherappportfolio

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import dagger.hilt.android.AndroidEntryPoint
import io.wookoo.common.ext.isFineLocationPermissionGranted
import io.wookoo.common.ext.openAndroidSettings
import io.wookoo.designsystem.ui.components.SharedCustomSnackBar
import io.wookoo.designsystem.ui.components.SharedLottieLoader
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.geolocation.WeatherLocationManager
import io.wookoo.permissions.PermissionDialog
import io.wookoo.permissions.Permissions
import io.wookoo.weatherappportfolio.navigation.Navigation
import javax.inject.Inject

// private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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
            var snackBarMessage by remember { mutableStateOf("") }
            var isSnackbarVisible by remember { mutableStateOf(false) }
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
                if (this.isFineLocationPermissionGranted()) {
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
                                onShowSnackBar = { message ->
                                    snackBarMessage = message
                                    isSnackbarVisible = true
                                }
                            )
                            SharedCustomSnackBar(
                                message = snackBarMessage,
                                isVisible = isSnackbarVisible,
                                onDismiss = { isSnackbarVisible = false }
                            )
                        }

                        else -> Unit
                    }
                }
            }
        }
    }
}
