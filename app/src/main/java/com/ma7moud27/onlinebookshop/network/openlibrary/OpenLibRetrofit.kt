package com.ma7moud27.onlinebookshop.network.openlibrary

import com.google.gson.GsonBuilder
import com.ma7moud27.onlinebookshop.model.author.Bio
import com.ma7moud27.onlinebookshop.model.book.Notes
import com.ma7moud27.onlinebookshop.model.work.Description
import com.ma7moud27.onlinebookshop.utils.BioDeserializer
import com.ma7moud27.onlinebookshop.utils.Constants
import com.ma7moud27.onlinebookshop.utils.DescriptionDeserializer
import com.ma7moud27.onlinebookshop.utils.deserializer.NoteDeserializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object OpenLibRetrofit {
    private val gson = GsonBuilder()
        .serializeNulls()
        .registerTypeAdapter(Description::class.java, DescriptionDeserializer())
        .registerTypeAdapter(Bio::class.java, BioDeserializer())
        .registerTypeAdapter(Notes::class.java, NoteDeserializer())
        .create()

    private val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY,
    )

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.OPEN_LIBRARY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
}
