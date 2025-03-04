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
import androidx.fragment.compose.AndroidFragment
import androidx.fragment.compose.rememberFragmentState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WeeklyPageScreen(
    onBackIconClick: () -> Unit,
) {
    val state = rememberFragmentState()

    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets.add(
                    androidx.compose.foundation.layout.WindowInsets.displayCutout.only(
                        WindowInsetsSides.Horizontal
                    )
                ),
                title = {},
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
            fragment.setOnBackIconClickListener(onBackIconClick)
        }
    }
}
