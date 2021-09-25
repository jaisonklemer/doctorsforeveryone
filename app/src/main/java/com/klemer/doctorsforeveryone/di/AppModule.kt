package com.klemer.doctorsforeveryone.di

import com.klemer.doctorsforeveryone.services.HealthServiceAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun getInstance(path: String): Retrofit {
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(path)
            .build()
    }

    @Provides
    fun getHealthService(): HealthServiceAPI {
        return getInstance("https://newsapi.org/").create(HealthServiceAPI::class.java)
    }

}
