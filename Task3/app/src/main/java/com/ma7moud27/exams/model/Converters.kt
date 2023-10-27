package com.ma7moud27.exams.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromExamResultsList(results: List<Result>): String {
        return gson.toJson(results)
    }

    @TypeConverter
    fun toExamResultsList(resultsString: String): List<Result> {
        val type: Type = object : TypeToken<List<Result>>() {}.type
        return gson.fromJson(resultsString, type)
    }

    @TypeConverter
    fun fromQuestionsList(questions: List<Question>): String {
        return gson.toJson(questions)
    }

    @TypeConverter
    fun toQuestionsList(questionsString: String): List<Question> {
        val type: Type = object : TypeToken<List<Question>>() {}.type
        return gson.fromJson(questionsString, type)
    }

    @TypeConverter
    fun fromAnswersList(answers: List<String>): String {
        return gson.toJson(answers)
    }

    @TypeConverter
    fun toAnswersList(answersString: String): List<String> {
        val type: Type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(answersString, type)
    }
}
