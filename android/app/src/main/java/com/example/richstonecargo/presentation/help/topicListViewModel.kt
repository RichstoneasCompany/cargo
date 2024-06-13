package com.example.richstonecargo.presentation.help

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.model.Topic
import com.example.richstonecargo.domain.model.TopicListState
import com.example.richstonecargo.domain.use_case.topic_list.TopicListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicListViewModel @Inject constructor(
    private val topicListUseCase: TopicListUseCase
) : ViewModel() {
    private val _state = MutableLiveData<TopicListState>()
    val state: LiveData<TopicListState> = _state

    init {
        loadTopics()
    }

    private fun loadTopics() {
        viewModelScope.launch {
            topicListUseCase()
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = TopicListState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _state.value = TopicListState(topics = result.data)
                        }

                        is Resource.Error -> {
                            _state.value = TopicListState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                }
                .catch { e ->
                    Log.e("loadTopics", "${e.message} ${e.stackTraceToString()}")
                    _state.value = TopicListState(
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
                .launchIn(viewModelScope)
        }
    }

    fun onTopicSelected(navController: NavController, topic: Topic) {
        navController.navigate("topic_detail_screen/${topic.id}")
    }
}
