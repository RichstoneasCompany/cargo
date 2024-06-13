package com.example.richstonecargo.presentation.help

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.model.QuestionsState
import com.example.richstonecargo.domain.repository.CargoRepository
import com.example.richstonecargo.domain.use_case.questions.QuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val questionsUseCase: QuestionsUseCase,
    private val cargoRepository: CargoRepository
) : ViewModel() {
    private val _state = MutableLiveData<QuestionsState>()
    val state: LiveData<QuestionsState> = _state

    fun loadQuestionsDetail(topicId: Long) {
        viewModelScope.launch {
            _state.value = QuestionsState(isLoading = true)

            try {
                val topicName = cargoRepository.getTopicNameById(topicId)
                questionsUseCase(topicId)
                    .onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.value =
                                    QuestionsState(isLoading = true, topicName = topicName)
                            }

                            is Resource.Success -> {
                                Log.d("QuestionsViewModel", "Questions loaded: ${result.data}")
                                _state.value = QuestionsState(
                                    questions = result.data ?: emptyList(),
                                    topicName = topicName
                                )
                            }

                            is Resource.Error -> {
                                Log.e(
                                    "QuestionsViewModel",
                                    "Error loading questions: ${result.message}"
                                )
                                _state.value = QuestionsState(
                                    error = result.message ?: "An unexpected error occurred",
                                    topicName = topicName
                                )
                            }
                        }
                    }
                    .catch { e ->
                        Log.e("QuestionsViewModel", "Exception: ${e.message}")
                        _state.value = QuestionsState(
                            error = e.message ?: "An unexpected error occurred",
                            topicName = topicName
                        )
                    }
                    .launchIn(this)
            } catch (e: Exception) {
                Log.e("QuestionsViewModel", "Exception: ${e.message}")
                _state.value = QuestionsState(error = e.message ?: "An unexpected error occurred")
            }
        }
    }
}