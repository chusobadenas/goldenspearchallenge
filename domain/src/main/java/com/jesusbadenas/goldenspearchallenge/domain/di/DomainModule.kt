package com.jesusbadenas.goldenspearchallenge.domain.di

import com.jesusbadenas.goldenspearchallenge.domain.repositories.AuthRepository
import com.jesusbadenas.goldenspearchallenge.domain.repositories.SearchRepository
import org.koin.dsl.module

val domainModule = module {
    factory { AuthRepository(get()) }
    factory { SearchRepository(get()) }
}
