package com.example.richstonecargo.domain.model

data class Question(
    val id: Long,
    val question: String,
    val answer: String,
)

data class QuestionsState(
    val isLoading: Boolean = false,
    val questions: List<Question> = emptyList(),
    val error: String = "",
    val topicName: String = ""
)



