package com.example.richstonecargo.presentation.help

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.richstonecargo.data.remote.dto.QuestionAddDto
import com.example.richstonecargo.data.remote.dto.TopicRequestDto

class HelpViewModel : ViewModel() {
    private val _topics = MutableLiveData<List<TopicRequestDto>>()
    val topics: LiveData<List<TopicRequestDto>> = _topics

    private val _questions = MutableLiveData<List<QuestionAddDto>>()
    val questions: LiveData<List<QuestionAddDto>> = _questions

    init {
        loadTopics()
        loadQuestions()
    }

    private fun loadTopics() {
        _topics.value = listOf(
            TopicRequestDto("Топ 10 вопросов"),
            TopicRequestDto("Регистрация и Авторизация"),
            TopicRequestDto("Рейсы и подготовка к рейсам"),
            TopicRequestDto("Зарплата"),
            TopicRequestDto("Действие в чрезвычайных ситуациях")
        )
    }

    private fun loadQuestions() {
        // This would typically be replaced by a call to a repository method that fetches questions from a server
        _questions.value = listOf(
            QuestionAddDto(
                1, "Что нужно сделать перед выходом в рейс?", "Проверьте шины \n" +
                        "Проверьте давление в каждой шине с помощью манометра и убедитесь, что оно соответствует рекомендуемому уровню.\n" +
                        "Проверьте приборы и светоотражатели\n" +
                        "Важнейшим аспектом безопасной поездки является видимость сигнальных обозначений.\n" +
                        "Проверьте тормоза\n" +
                        "Отказ тормозов может привести к катастрофе; необходимость регулярных проверок тормозной системы не подлежит обсуждению.\n" +
                        "Проверьте двигатель\n" +
                        "Осуществите проверку двигателя, осмотрев ремни, шланги и жидкости на предмет утечек или повреждений.\n" +
                        "Проверьте топливный бак и аккумулятор. "
            ),
            QuestionAddDto(
                2,
                "Какие документы нужны для рейса?",
                "Список необходимых документов..."
            )
        )
    }

    fun addQuestion(topicId: Long, question: String, answer: String) {
        // Add a new question to the list, ideally, this would post data to a backend API
        val updatedQuestions = _questions.value?.toMutableList() ?: mutableListOf()
        updatedQuestions.add(QuestionAddDto(topicId, question, answer))
        _questions.value = updatedQuestions
    }
}
