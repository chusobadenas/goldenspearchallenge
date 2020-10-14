package com.jesusbadenas.goldenspearchallenge.di

import com.jesusbadenas.goldenspearchallenge.artist.ArtistAdapter
import com.jesusbadenas.goldenspearchallenge.artist.ArtistFragment
import com.jesusbadenas.goldenspearchallenge.navigation.Navigator
import com.jesusbadenas.goldenspearchallenge.viewmodel.ArtistViewModel
import com.jesusbadenas.goldenspearchallenge.viewmodel.SplashViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { ArtistAdapter() }
    fragment { ArtistFragment() }
    single { Navigator() }
    viewModel { ArtistViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}
