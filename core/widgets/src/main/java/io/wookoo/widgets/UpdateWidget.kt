package io.wookoo.widgets

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager

suspend fun <T : GlanceAppWidget> updateWidget(appContext: Context, widgetClass: Class<T>) {
    GlanceAppWidgetManager(appContext).getGlanceIds(widgetClass)
        .forEach { id ->
            widgetClass.getConstructor().newInstance().update(appContext, id)
        }
}
