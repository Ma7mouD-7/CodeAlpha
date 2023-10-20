package com.ma7moud27.shelfy.network.openlibrary

import com.google.gson.GsonBuilder
import com.ma7moud27.shelfy.model.author.Bio
import com.ma7moud27.shelfy.model.book.Notes
import com.ma7moud27.shelfy.model.work.Description
import com.ma7moud27.shelfy.utils.Constants
import com.ma7moud27.shelfy.utils.deserializer.BioDeserializer
import com.ma7moud27.shelfy.utils.deserializer.DescriptionDeserializer
import com.ma7moud27.shelfy.utils.deserializer.NoteDeserializer
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object OpenLibRetrofit {
    private val gson = GsonBuilder().serializeNulls()
        .registerTypeAdapter(Description::class.java, DescriptionDeserializer())
        .registerTypeAdapter(Bio::class.java, BioDeserializer())
        .registerTypeAdapter(Notes::class.java, NoteDeserializer()).create()

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val onlineInterceptor = Interceptor { chain ->
        val response: Response = chain.proceed(chain.request())
        val maxAge = 60
        response.newBuilder().header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma").build()
    }

    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
            .cache(Cache(File.createTempFile("cache", null), 10 * 1024 * 1024))
            .connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(Constants.OPEN_LIBRARY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()
    }
}
