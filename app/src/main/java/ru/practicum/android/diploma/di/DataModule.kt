package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.db.AppDatabase
import ru.practicum.android.diploma.filter.domain.converter.VacancyDbMapper
import ru.practicum.android.diploma.core.network.HhApiService
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.core.network.RetrofitNetworkClient
import ru.practicum.android.diploma.favorites.data.FavouritesDbMapper

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
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db").build()
    }

    factory {
        FavouritesDbMapper()
    }

    factory {
        VacancyDbMapper()
    }
}
