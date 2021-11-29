package com.shevel.pixabaychallenge.di

import com.shevel.pixabaychallenge.BuildConfig
import com.shevel.pixabaychallenge.network.PixabayApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val TIMEOUT_READ: Long = 40
const val TIMEOUT_CONNECTION: Long = 30

@Module
class NetworkModule {

    @Provides
    fun provideRestApiService(retrofit: Retrofit): PixabayApi =
        retrofit.create(PixabayApi::class.java)

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    fun getSimpleOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient().newBuilder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

        httpClientBuilder.addInterceptor(getQueryInterceptor())
        httpClientBuilder.readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)

        return httpClientBuilder.build()
    }

    private fun getQueryInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("key", BuildConfig.API_KEY)
                    .build()

                val requestBuilder = original.newBuilder()
                    .url(url)
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        }
    }
}