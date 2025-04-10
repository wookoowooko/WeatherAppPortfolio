package io.wookoo.weatherappportfolio

import android.Manifest
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.wookoo.common.ext.hasLocationPermissions
import io.wookoo.common.ext.openAndroidSettings
import io.wookoo.designsystem.ui.components.SharedCustomSnackBar
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.service.IConnectivityObserver
import io.wookoo.domain.sync.ISyncManager
import io.wookoo.permissions.PermissionDialog
import io.wookoo.permissions.Permissions
import io.wookoo.weatherappportfolio.appstate.rememberAppState
import io.wookoo.weatherappportfolio.navigation.Navigation
import io.wookoo.weatherappportfolio.splash.SplashViewModel
import io.wookoo.worker.utils.Sync
import io.wookoo.worker.utils.Sync.initializeOneTime
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var permissions: Permissions

    @Inject
    lateinit var dataStore: IDataStoreRepo

    @Inject
    lateinit var connectivityObserver: IConnectivityObserver

    @Inject
    lateinit var syncManager: ISyncManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.splashState.value.shouldKeepSplashScreen()
        }

        lifecycleScope.launch {
            syncManager.syncChannel.collect {
                Sync.initializeReSync(this@MainActivity.applicationContext)
            }
        }

        enableEdgeToEdge()

        setContent {
//            requestedOrientation =
//                if (isCompactDevice()) ActivityInfo.SCREEN_ORIENTATION_PORTRAIT else ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

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

            val isOffline by appState.isOffline.collectAsStateWithLifecycle()
            var snackBarMessage by rememberSaveable { mutableStateOf("") }
            var isSnackBarVisible by rememberSaveable { mutableStateOf(false) }
            val notConnectedMessage =
                stringResource(io.wookoo.androidresources.R.string.error_no_internet)
            var firstLaunched by rememberSaveable { mutableStateOf(true) }
            var snackBarColor by remember { mutableStateOf(Color.Red) }

            LaunchedEffect(isOffline) {
                if (isOffline) {
                    snackBarMessage = notConnectedMessage
                    isSnackBarVisible = true
                    snackBarColor = Color.Red
                    firstLaunched = false
                } else {
                    if (!firstLaunched) {
                        snackBarMessage = "Internet restored"
                        isSnackBarVisible = true
                        snackBarColor = Color.Green
                    }
                }
            }

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
                    Navigation(
                        startDestination = startDestination,
                        onRequestLocationPermission = {
                            locationPermissionResultLauncher.launch(
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        },
                        onShowSnackBar = { message ->
                            snackBarMessage = message
                            isSnackBarVisible = true
                        },
                        onSyncRequest = { geoItemId, isNeedToUpdate ->
                            initializeOneTime(
                                context = this@MainActivity,
                                locationId = geoItemId,
                                isNeedToUpdate = isNeedToUpdate
                            )
                        }
                    )
                }

                SharedCustomSnackBar(
                    snackBarColor = snackBarColor,
                    message = snackBarMessage,
                    isVisible = isSnackBarVisible,
                    onDismiss = { isSnackBarVisible = false }
                )
            }
        }
    }
}
