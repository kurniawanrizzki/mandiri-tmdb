package com.mandiri.tmdb.data

import com.mandiri.tmdb.data.movie.MovieRepositoryImpl
import com.mandiri.tmdb.data.movie.api.MovieApi
import com.mandiri.tmdb.domain.movie.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindMovieRepository(repo: MovieRepositoryImpl): MovieRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)
}