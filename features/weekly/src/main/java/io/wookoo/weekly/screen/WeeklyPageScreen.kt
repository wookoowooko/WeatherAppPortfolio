package io.wookoo.weekly.screen

import android.util.Log
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.fragment.compose.AndroidFragment
import androidx.fragment.compose.rememberFragmentState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.weekly.mvi.WeeklyViewModel
import io.wookoo.weekly.navigation.WeeklyRoute
import io.wookoo.weekly.screen.RouteConsts.GEO_ITEM_ID_KEY

private const val TAG = "WeeklyPageScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WeeklyPageScreen(
    onBackIconClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    navBackStackEntry: NavBackStackEntry
) {
    val viewModel = hiltViewModel<WeeklyViewModel>()
    val args = navBackStackEntry.toRoute<WeeklyRoute>()
    val state = rememberFragmentState()
    val stateVm by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets.add(
                    androidx.compose.foundation.layout.WindowInsets.displayCutout.only(
                        WindowInsetsSides.Horizontal
                    )
                ),
                title = {
                    SharedText(text = stateVm.cityName)
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackIconClick
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) {
        AndroidFragment<WeeklyFragment>(
            arguments = bundleOf(
                GEO_ITEM_ID_KEY to args.geoItemId,
            ),
            fragmentState = state,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .windowInsetsPadding(
                    androidx.compose.foundation.layout.WindowInsets.displayCutout.only(
                        WindowInsetsSides.Horizontal
                    )
                )
        ) { fragment ->
//            val insets = ViewCompat.getRootWindowInsets(fragment.requireView())
//            val cutoutInsets = insets?.getInsets(WindowInsetsCompat.Type.displayCutout())
//            cutoutInsets?.let {
//                fragment.view?.findViewById<View>(R.id.constraintContainer)?.apply {
//                    setPadding(it.left, 0, it.right, 0)
//                }
//            }

            fragment.onShowSnackBar = onShowSnackBar
        }
    }
}

internal object RouteConsts {
    const val GEO_ITEM_ID_KEY = "geoItemId"
}
