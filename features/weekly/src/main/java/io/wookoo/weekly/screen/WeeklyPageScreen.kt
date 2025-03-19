package io.wookoo.weekly.screen

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
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.fragment.compose.AndroidFragment
import androidx.fragment.compose.rememberFragmentState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.weekly.navigation.WeeklyRoute
import io.wookoo.weekly.screen.RouteConsts.GEO_ITEM_ID_KEY
import io.wookoo.weekly.screen.RouteConsts.LATITUDE_KEY
import io.wookoo.weekly.screen.RouteConsts.LONGITUDE_KEY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WeeklyPageScreen(
    onBackIconClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    navBackStackEntry: NavBackStackEntry,
) {
    val args = navBackStackEntry.toRoute<WeeklyRoute>()
    val state = rememberFragmentState()


    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets.add(
                    androidx.compose.foundation.layout.WindowInsets.displayCutout.only(
                        WindowInsetsSides.Horizontal
                    )
                ),
                title = {
                    SharedText(text = "hz")
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
                LATITUDE_KEY to args.latitude,
                LONGITUDE_KEY to args.longitude,
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
    const val LATITUDE_KEY = "latitude"
    const val LONGITUDE_KEY = "longitude"
}


