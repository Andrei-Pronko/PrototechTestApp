package com.mentarey.prototech.test.app.di

import android.content.Context
import com.mentarey.prototech.test.app.data.pref.SettingsManager
import com.mentarey.prototech.test.app.data.pref.SettingsManagerImpl
import com.mentarey.prototech.test.app.data.pref.UserAccountRepository
import com.mentarey.prototech.test.app.data.pref.UserAccountRepositoryImpl
import com.mentarey.prototech.test.app.data.retrofit.PrototechApi
import com.mentarey.prototech.test.app.domain.LoginInteractor
import com.mentarey.prototech.test.app.domain.SignalsInteractor
import com.mentarey.prototech.test.app.ui.login.LoginViewModel
import com.mentarey.prototech.test.app.ui.signals.SignalViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://client-api.instaforex.com/"

private val retrofitModule = module {
    factory<Converter.Factory> { GsonConverterFactory.create() }
    factory {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        OkHttpClient.Builder().apply {
            addInterceptor(get<HttpLoggingInterceptor>())
        }.build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(get<Converter.Factory>())
            .build()
    }
    single { get<Retrofit>().create(PrototechApi::class.java) }
}

private val preferencesModule = module {
    single {
        androidContext().getSharedPreferences(
            "com.mentarey.prototech.test.app.user",
            Context.MODE_PRIVATE
        )
    }
    factory<SettingsManager> { SettingsManagerImpl(get()) }
    single<UserAccountRepository> { UserAccountRepositoryImpl(get()) }
}

private val loginModule = module {
    factory { LoginInteractor(get(), get()) }
    viewModel { LoginViewModel(get()) }
}

private val signalModule = module {
    factory { SignalsInteractor(get(), get()) }
    viewModel { SignalViewModel(get()) }
}

val koinModules = listOf(retrofitModule, preferencesModule, loginModule, signalModule)