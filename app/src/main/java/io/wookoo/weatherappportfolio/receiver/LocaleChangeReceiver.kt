package io.wookoo.weatherappportfolio.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.wookoo.worker.utils.Sync

class LocaleChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_LOCALE_CHANGED) {
            Sync.initializeReSync(context.applicationContext)
        }
    }
}
