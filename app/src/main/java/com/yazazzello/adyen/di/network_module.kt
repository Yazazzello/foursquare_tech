package com.yazazzello.adyen.di

import com.yazazzello.adyen.di.DatasourceProperties.SERVER_URL
import com.yazazzello.adyen.restapi.FoursquareApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = applicationContext {
    // provided web components
    bean { createOkHttpClient() }
    // Fill property
    bean { createWebService<FoursquareApi>(get(), getProperty(SERVER_URL)) }
}

object DatasourceProperties {
    const val SERVER_URL = "SERVER_URL"
    const val CLIENT_ID = "F_CLIENT_ID"
    const val CLIENT_SECRET = "F_CLIENT_SECRET"
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}
