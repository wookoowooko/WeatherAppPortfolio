package io.wookoo.designsystem.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import io.wookoo.design.system.R

@Composable
fun SharedLottieLoader(
    modifier: Modifier = Modifier,
) {
    val isPlaying by remember { mutableStateOf(true) }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        speed = 1.9f,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            modifier = Modifier.fillMaxSize(),
            composition = composition,
            progress = { progress }
        )
    }
}

@Composable
@Preview
private fun LottieLoaderPreview() {
    SharedLottieLoader()
}
