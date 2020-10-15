package com.jesusbadenas.goldenspearchallenge.domain.di

import com.jesusbadenas.goldenspearchallenge.domain.repositories.AuthRepository
import org.koin.dsl.module

val domainModule = module {
    factory { AuthRepository(get(), get()) }
}
