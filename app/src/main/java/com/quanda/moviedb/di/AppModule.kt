package com.quanda.moviedb.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    private val context: Context

    init {
        context = application
    }

    @Provides
    @Singleton
    fun provideApplication() = application

    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideResources() = context.resources

    @Provides
    @Singleton
    fun provideAppDatabase() = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideMovieDao() = provideAppDatabase().movieDao()
}