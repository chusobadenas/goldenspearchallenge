package com.jesusbadenas.goldenspearchallenge.data.di

import android.content.Context
import android.util.Base64
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jesusbadenas.goldenspearchallenge.data.api.APIService
import com.jesusbadenas.goldenspearchallenge.data.api.AuthorizationService
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_NAME = "api"
private const val AUTH_NAME = "auth"
private const val AUTHORIZATION = "Authorization"
private const val CACHE_SIZE_MB: Long = 5 * 1024 * 1024
private const val CLIENT_ID = "clientId"
private const val CLIENT_SECRET = "clientSecret"
private const val DATE_FORMAT = "YYYY-MM-DDTHH:MM:SSZ"
private const val HEADER_INTERCEPTOR = "headerInterceptor"

val dataModule = module {
    factory(named(HEADER_INTERCEPTOR)) {
        provideHeaderInterceptor(
            getAuthorization(
                getProperty(CLIENT_ID),
                getProperty(CLIENT_SECRET)
            )
        )
    }
    factory {
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
    }
    factory<Gson> {
        GsonBuilder().setDateFormat(DATE_FORMAT).create()
    }
    factory {
        provideOkHttpClient(
            androidContext(),
            get(named(HEADER_INTERCEPTOR)),
            get()
        )
    }
    factory { provideAPIService(get(named(API_NAME))) }
    factory { provideAuthorizationService(get(named(AUTH_NAME))) }
    single(named(API_NAME)) { provideRetrofit(get(), get(), APIService.BASE_URL) }
    single(named(AUTH_NAME)) { provideRetrofit(get(), get(), AuthorizationService.BASE_URL) }
}

private fun getAuthorization(clientId: String, clientSecret: String): String =
    Base64.encodeToString("$clientId:$clientSecret".toByteArray(), Base64.DEFAULT)

private fun provideHeaderInterceptor(authorization: String): Interceptor =
    Interceptor { chain ->
        val request = chain.request().newBuilder().apply {
            header(AUTHORIZATION, authorization)
        }.build()
        chain.proceed(request)
    }

private fun provideOkHttpClient(
    context: Context,
    headerInterceptor: Interceptor,
    logInterceptor: HttpLoggingInterceptor
): OkHttpClient =
    OkHttpClient.Builder().apply {
        // Enable cache
        val myCache = Cache(context.cacheDir, CACHE_SIZE_MB)
        cache(myCache)
        // Set header
        addInterceptor(headerInterceptor)
        // Enable logging
        if (BuildConfig.DEBUG) {
            addInterceptor(logInterceptor)
        }
    }.build()

private fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson, baseUrl: String): Retrofit =
    Retrofit.Builder().apply {
        baseUrl(baseUrl)
        client(okHttpClient)
        addConverterFactory(GsonConverterFactory.create(gson))
    }.build()

private fun provideAPIService(retrofit: Retrofit): APIService =
    retrofit.create(APIService::class.java)

private fun provideAuthorizationService(retrofit: Retrofit): AuthorizationService =
    retrofit.create(AuthorizationService::class.java)
