package ru.practicum.android.diploma.di

import android.content.Context
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.core.network.HhApiService
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.core.network.RetrofitNetworkClient

private const val BASE_URL = "https://api.hh.ru/"
private const val SHARED_PREFS = "app_preferences"

val dataModule = module {

    single<HhApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HhApiService::class.java)
    }

    factory { Gson() }

    single<NetworkClient> {
        RetrofitNetworkClient(
            androidContext(),
            hhApiService = get()
        )
    }

    single {
        androidContext()
            .getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    }
}
