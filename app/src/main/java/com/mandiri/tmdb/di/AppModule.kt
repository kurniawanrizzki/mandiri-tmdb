package com.mandiri.tmdb.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mandiri.tmdb.BuildConfig
import com.mandiri.tmdb.data.common.RequestInterceptor
import com.mandiri.tmdb.data.common.UTCAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }


    @Provides
    fun provideOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
            readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            addInterceptor(RequestInterceptor())
            if (BuildConfig.DEBUG) addInterceptor(logger)
        }.build()

    @Provides
    fun provideGson(): Gson =
        GsonBuilder().apply {
            registerTypeAdapter(Date::class.java, UTCAdapter())
        }.create()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create(gson))
            client(client)
            baseUrl("https://api.themoviedb.org/3/")
        }.build()
}

private const val NETWORK_TIME_OUT = 15L
private const val READ_TIME_OUT = 30L
private const val WRITE_TIME_OUT = 15L