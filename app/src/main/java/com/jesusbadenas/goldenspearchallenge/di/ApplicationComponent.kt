package com.jesusbadenas.goldenspearchallenge.di

import com.jesusbadenas.goldenspearchallenge.data.di.dataModule
import com.jesusbadenas.goldenspearchallenge.domain.di.domainModule

val appComponent = listOf(
    dataModule,
    domainModule,
    appModule
)
