package com.vti.messageapp.di

import com.vti.messageapp.data.datasources.local.app.ConversationDataSourceImpl
import com.vti.messageapp.data.datasources.local.app.MessageLocalDataSourceImpl
import com.vti.messageapp.data.datasources.local.app.UserLocalDataSourceImpl
import com.vti.messageapp.data.datasources.local.sources.ConversationDataSource
import com.vti.messageapp.data.datasources.local.sources.MessageLocalDataSource
import com.vti.messageapp.data.datasources.local.sources.UserLocalDataSource
import com.vti.messageapp.data.repositories.AuthRepositoryImpl
import com.vti.messageapp.data.repositories.ConversationRepositoryImpl
import com.vti.messageapp.data.repositories.MessageRepositoryImpl
import com.vti.messageapp.data.repositories.UserRepositoryImpl
import com.vti.messageapp.domain.repositories.AuthRepository
import com.vti.messageapp.domain.repositories.ConversationRepository
import com.vti.messageapp.domain.repositories.MessageRepository
import com.vti.messageapp.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindConversationRepository(conversationRepository: ConversationRepositoryImpl): ConversationRepository

    @Binds
    @Singleton
    abstract fun bindConversationLocalDataSource(conversationDataSource: ConversationDataSourceImpl): ConversationDataSource

    @Binds
    @Singleton
    abstract fun bindUserLocalDataSource(userLocalDataSource: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindMessageLocalDataSource(messageLocalDataSourceImpl: MessageLocalDataSourceImpl): MessageLocalDataSource

    @Binds
    @Singleton
    abstract fun bindMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository
}
