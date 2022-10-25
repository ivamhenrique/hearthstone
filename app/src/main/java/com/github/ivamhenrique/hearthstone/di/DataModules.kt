package com.github.ivamhenrique.hearthstone.di

import com.github.ivamhenrique.hearthstone.data.network.AuthInterceptor
import com.github.ivamhenrique.hearthstone.data.repositories.HearthstoneApi
import com.github.ivamhenrique.hearthstone.data.repositories.HearthstoneRepository
import okhttp3.OkHttpClient
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModules {

    private const val API_URL = "https://omgvamp-hearthstone-v1.p.rapidapi.com/"

    private val moduleDI = module {
        single { HearthstoneRepository(get(), get()) }
    }

    private val networkModule = module {
        factory { AuthInterceptor() }
        factory { provideOkHttpClient(get()) }
        factory { provideLupeonApi(get()) }
        single { provideRetrofit(get()) }
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun provideLupeonApi(retrofit: Retrofit) = retrofit.create(HearthstoneApi::class.java)

    private fun provideOkHttpClient(authInterceptor: AuthInterceptor) =
        OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()

    fun loadModule() {
        loadKoinModules(
            listOf(
                moduleDI, networkModule
            )
        )
    }
}