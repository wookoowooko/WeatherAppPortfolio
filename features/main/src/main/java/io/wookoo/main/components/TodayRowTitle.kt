package io.wookoo.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.components.SharedText

@Composable
internal fun TodayRowTitle(
    modifier: Modifier = Modifier,
    onNextSevenDaysClick: () -> Unit,
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SharedText(
            text = stringResource(io.wookoo.androidresources.R.string.today_titile),
            weight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        TextButton(
            onClick = { onNextSevenDaysClick() },
            shape = MaterialTheme.shapes.small
        ) {
            SharedText(
                text = stringResource(io.wookoo.androidresources.R.string.next_7_days_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(
                onClick = onNextSevenDaysClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
@Preview
private fun TodayRowTitlePreview() {
    TodayRowTitle(
        onNextSevenDaysClick = {}
    )
}
