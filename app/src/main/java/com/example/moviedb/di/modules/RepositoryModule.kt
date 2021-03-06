package com.example.moviedb.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.room.Room
import com.example.moviedb.data.constants.Constants
import com.example.moviedb.data.local.dao.MovieDao
import com.example.moviedb.data.local.db.AppDatabase
import com.example.moviedb.data.local.pref.AppPrefs
import com.example.moviedb.data.local.pref.PrefHelper
import com.example.moviedb.data.remote.ApiService
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.data.repository.impl.UserRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext context: Context) : Context = context

    @Singleton
    @Provides
    fun provideSharedPrefs(context: Context) : SharedPreferences
            = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context) : AppDatabase
            = Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
                .build()

    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase) : MovieDao
            = appDatabase.movieDao()

    @Singleton
    @Provides
    fun provideGson() : Gson = Gson()

    @Singleton
    @Provides
    fun providePrefHelper(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ) : PrefHelper = AppPrefs(sharedPreferences, gson)

    @Singleton
    @Provides
    fun provideUserRepository(
        apiService: ApiService,
        movieDao: MovieDao
    ) : UserRepository = UserRepositoryImpl(apiService, movieDao)

    @Singleton
    @Provides
    fun provideResources(context: Context) : Resources = context.resources
}