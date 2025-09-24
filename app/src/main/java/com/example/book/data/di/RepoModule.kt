package com.example.book.data.di

import com.example.book.data.di.RepoImpl.AllAuthRepoImpl
import com.example.book.data.di.RepoImpl.AllBookRepoImpl
import com.example.book.domain.repo.AllBookRepo
import com.example.book.domain.repo.AllAuthRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun bindAllBookRepo(
        impl: AllBookRepoImpl
    ): AllBookRepo

    @Binds
    @Singleton
    abstract fun bindAuthRepo(
        impl: AllAuthRepoImpl
    ): AllAuthRepo
}
