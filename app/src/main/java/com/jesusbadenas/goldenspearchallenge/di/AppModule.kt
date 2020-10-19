package com.jesusbadenas.goldenspearchallenge.di

import androidx.recyclerview.widget.RecyclerView
import com.jesusbadenas.goldenspearchallenge.artist.ArtistAdapter
import com.jesusbadenas.goldenspearchallenge.artist.ArtistFragment
import com.jesusbadenas.goldenspearchallenge.calendar.CalendarEventsManager
import com.jesusbadenas.goldenspearchallenge.navigation.Navigator
import com.jesusbadenas.goldenspearchallenge.viewmodel.ArtistViewModel
import com.jesusbadenas.goldenspearchallenge.viewmodel.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { ArtistAdapter(get()) }
    factory { RecyclerView.RecycledViewPool() }
    fragment { ArtistFragment() }
    single { CalendarEventsManager(androidContext()) }
    single { Navigator() }
    viewModel { ArtistViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}
