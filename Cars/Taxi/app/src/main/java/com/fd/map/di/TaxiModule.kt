package com.fd.map.di

import com.fd.map.data.remote.TaxiApi
import com.fd.map.data.repository.RemoteDataSource
import com.fd.map.data.repository.TaxisRepositoryImpl
import com.fd.map.domain.common.Constants.BASE_URL
import com.fd.map.domain.repository.TaxiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TaxiModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(): TaxiApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaxiApi::class.java)


    @Provides
    @Singleton
    fun provideTaxisRepository(remoteDataSource: RemoteDataSource): TaxiRepository {
        return TaxisRepositoryImpl(remoteDataSource)
    }
}