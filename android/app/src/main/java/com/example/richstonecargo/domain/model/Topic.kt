package com.example.richstonecargo.domain.model

data class Topic(
    val id: Long,
    val name: String
)

data class TopicListState(
    val isLoading: Boolean = false,
    val topics: List<Topic>? = emptyList(),
    val error: String = ""
)