package com.contactlist.currency.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import androidx.room.Room
import com.contactlist.currency.data.local.AppDatabase
import com.contactlist.currency.data.local.dao.ContactListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    internal fun provideAssetManager(context: Context): AssetManager {
        return context.assets
    }

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "contact_list-db")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideContactDao(db: AppDatabase): ContactListDao = db.contactListDao()
    /**
     * Provides Preferences object with MODE_PRIVATE
     */
    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE)

}
