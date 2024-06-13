package com.example.richstonecargo.data.remote.dto

import com.example.richstonecargo.domain.model.Topic

data class TopicDto(
    val id: Long,
    val name: String
)

fun TopicDto.toTopic(): Topic {
    return Topic(
        id = this.id,
        name = this.name
    )
}
