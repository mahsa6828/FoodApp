package com.example.foodapp.utils.di

import com.example.foodapp.data.server.ApiService
import com.example.foodapp.utils.BASE_URL
import com.example.foodapp.utils.CONNECTION_TIME
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideConnectionTime() = CONNECTION_TIME

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideClient(time:Long,interceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .writeTimeout(time,TimeUnit.SECONDS)
        .readTimeout(time,TimeUnit.SECONDS)
        .connectTimeout(time, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl : String,gson: Gson,client: OkHttpClient): ApiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)


}