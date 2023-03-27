package com.webronin_26.online_mart_admin.data

import com.webronin_26.online_mart_admin.data.source.AdminRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    private val adminRepository : AdminRepository = AdminRepository()

    @Provides
    fun provideOnlineMartRepository() : AdminRepository {
        return adminRepository
    }
}