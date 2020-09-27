package com.okan.paymentapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.okan.paymentapp.BuildConfig
import com.okan.paymentapp.api.PaymentAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("content-type","application/json")
                    builder.header("accept","application/json")
                    builder.header("x-ibm-client-id", BuildConfig.CLIENT_ID)
                    builder.header("x-ibm-client-secret", BuildConfig.CLIENT_SECRET)
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }.build()
        return Retrofit.Builder()
            .baseUrl("https://sandbox-api.payosy.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
    }

    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder): PaymentAPI {
        return retrofit.build()
            .create(PaymentAPI::class.java)
    }

    /**
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
    return Retrofit.Builder()
    .baseUrl("https://sandbox-api.payosy.com/api/")
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(UnsafeOkHttpClient.unsafeOkHttpClient)}
     */

}