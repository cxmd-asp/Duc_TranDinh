package com.vti.messageapp.di

import android.content.Context
import android.content.SharedPreferences
import com.vti.messageapp.data.datasources.local.AppDatabase
import com.vti.messageapp.shared.Constants.Companion.FILE_PATH
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(FILE_PATH, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext app: Context
    ) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideMessageDao(db: AppDatabase) = db.messageDao()

    @Singleton
    @Provides
    fun provideConversationDao(db: AppDatabase) = db.conversationDao()

    @Singleton
    @Provides
    fun provideParticipantDao(db: AppDatabase) = db.participantDao()
}
