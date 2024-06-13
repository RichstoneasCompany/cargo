package com.example.richstonecargo.data.remote.dto

import com.example.richstonecargo.domain.model.Question

data class QuestionDto(
    val id: Long?,
    val question: String?,
    val answer: String?
)

fun QuestionDto.toQuestion(): Question {
    return Question(
        id ?: 0,
        question ?: "",
        answer ?: "",
    )
}