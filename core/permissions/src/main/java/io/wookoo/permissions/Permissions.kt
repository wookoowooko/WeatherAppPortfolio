package io.wookoo.permissions

import androidx.compose.runtime.mutableStateListOf

class Permissions {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        if (visiblePermissionDialogQueue.isNotEmpty()) {
            visiblePermissionDialogQueue.remove(visiblePermissionDialogQueue.first())
        }
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean,
    ) {
        if (!isGranted) {
            visiblePermissionDialogQueue.add(permission)
        }
    }
}
