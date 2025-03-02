package io.wookoo.permissions

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PermissionsModule {

    @Provides
    fun providePermissions(): Permissions {
        return Permissions()
    }
}
