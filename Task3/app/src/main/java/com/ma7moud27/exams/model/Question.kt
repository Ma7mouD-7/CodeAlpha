package com.ma7moud27.exams.model

sealed class Question {
    data class MultipleChoice(
        val questionText: String = "",
        val questionPoints: Int = 0,
//    val answer: String,
        val choices: List<String> = listOf(),
    ) : Question()

    data class Essay(
        val questionText: String = "",
        val questionPoints: Int = 0,
        val maxWordsCount: Int = 0,
    ) : Question()

    data class ShortAnswer(
        val questionText: String = "",
        val questionPoints: Int = 0,
        val maxCharsCount: Int = 0,
    ) : Question()
}
