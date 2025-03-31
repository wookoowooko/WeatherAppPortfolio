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

/**
 * Annotation to mark methods that are covered by tests.
 * This is purely syntactic sugar with no impact on execution logic,
 * but it helps developers understand that a method has been tested.
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class CoveredByTest
