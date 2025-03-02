package io.wookoo.permissions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PermissionDialog(
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        modifier = modifier,
        icon = {
            Icon(Icons.Default.LocationOn, contentDescription = null)
        },
        title = {
            Text(text = stringResource(R.string.permission_required))
        },
        text = {
            val text = if (isPermanentlyDeclined) {
                R.string.denied_permanently
            } else {
                R.string.denied_perm
            }
            Text(
                text = stringResource(text),
            )
        },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = {
                    if (isPermanentlyDeclined) {
                        onGoToAppSettingsClick()
                    } else {
                        onOkClick()
                    }
                }
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.dismiss))
            }
        }
    )
}

@Composable
@Preview
private fun PermissionDialogPreview() {
    PermissionDialog(
        isPermanentlyDeclined = false,
        onDismiss = {},
        onOkClick = {},
        onGoToAppSettingsClick = {}
    )
}

@Composable
@Preview
private fun PermissionDialogPreview2() {
    PermissionDialog(
        isPermanentlyDeclined = true,
        onDismiss = {},
        onOkClick = {},
        onGoToAppSettingsClick = {}
    )
}
