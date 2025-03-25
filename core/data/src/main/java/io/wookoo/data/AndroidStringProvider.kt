package io.wookoo.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.wookoo.common.ext.asLocalizedString
import io.wookoo.common.ext.asLocalizedUiWeatherMap
import io.wookoo.common.ext.asUnitString
import io.wookoo.domain.enums.WeatherCondition
import io.wookoo.domain.units.WeatherUnit
import io.wookoo.domain.units.WindDirection
import io.wookoo.domain.utils.IStringProvider
import javax.inject.Inject

class AndroidStringProvider @Inject constructor(@ApplicationContext private val context: Context) :
    IStringProvider {
    override fun getString(resId: Int): String = context.getString(resId)
    override fun fromApiUnit(unit: WeatherUnit): String {
        return getString(unit.asUnitString())
    }

    override fun fromWeatherCondition(condition: WeatherCondition, isDay: Boolean): String {
        return getString(condition.asLocalizedUiWeatherMap(isDay).second)
    }

    override fun fromWindDirection(direction: WindDirection): String {
        return getString(direction.asLocalizedString())
    }
}
