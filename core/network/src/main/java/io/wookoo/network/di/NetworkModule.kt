package io.wookoo.network.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wookoo.network.api.AppOkHttpClient
import io.wookoo.network.api.IRetrofit
import io.wookoo.network.api.IWeatherService
import io.wookoo.network.api.WeatherServiceImpl
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkClient(): AppOkHttpClient {
        return AppOkHttpClient()
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: AppOkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppOkHttpClient.baseUrl)
            .client(client.getClient())
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideIRetrofit(retrofit: Retrofit): IRetrofit {
        return retrofit.create(IRetrofit::class.java)
    }
}


@Module
@InstallIn(SingletonComponent::class)
interface NetworkInterfaceModule {

    @Binds
    fun bindsIWeatherService(
        masterRepo: WeatherServiceImpl,
    ): IWeatherService
}
