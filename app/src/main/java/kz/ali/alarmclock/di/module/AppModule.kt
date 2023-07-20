package kz.ali.alarmclock.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.ali.alarmclock.data.local.DatabaseHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabaseHelper(application: Application): DatabaseHelper =
        DatabaseHelper(application.applicationContext)
}