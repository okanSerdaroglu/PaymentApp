package com.okan.paymentapp.di

import com.okan.paymentapp.api.NetworkMapper
import com.okan.paymentapp.api.PaymentAPI
import com.okan.paymentapp.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        paymentAPI: PaymentAPI,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(
            paymentAPI = paymentAPI,
            networkMapper = networkMapper
        )
    }

}