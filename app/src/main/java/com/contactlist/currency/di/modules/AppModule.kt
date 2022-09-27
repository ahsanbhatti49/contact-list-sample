package com.contactlist.currency.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.contactlist.currency.data.api.ApiServices
import com.contactlist.currency.data.api.network.LiveDataCallAdapterFactoryForRetrofit
import com.contactlist.currency.data.local.AppDatabase
import com.contactlist.currency.data.local.dao.ContactListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideNewsService(): ApiServices {
        return Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactoryForRetrofit())
            .build()
            .create(ApiServices::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "contact_list-db")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): ContactListDao = db.contactListDao()


}
