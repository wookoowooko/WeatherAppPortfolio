package io.wookoo.domain.annotations

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeoCodingApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ReverseGeoCodingApi

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class StoreViewModelScope
