package io.wookoo.weatherappportfolio.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wookoo.weatherappportfolio.MainActivity

@Module
@InstallIn(SingletonComponent::class)
class ActivityProviderModule {

    @Provides
    fun provideTargetActivity(): Class<out Activity> = MainActivity::class.java
}
