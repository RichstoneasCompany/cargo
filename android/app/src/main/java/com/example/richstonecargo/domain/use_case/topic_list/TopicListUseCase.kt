package com.example.richstonecargo.domain.use_case.topic_list

import android.util.Log
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.model.Topic
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TopicListUseCase @Inject constructor(
    private val repository: CargoRepository
) {
    operator fun invoke(): Flow<Resource<List<Topic>>> = flow {
        try {
            Log.d("TopicListUseCase", "started")
            emit(Resource.Loading<List<Topic>>())
            val topicList = repository.getTopicList()
            Log.d("TopicListUseCase received response", "$topicList")
            emit(Resource.Success<List<Topic>>(topicList))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Topic>>("HTTP error: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Topic>>("Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error<List<Topic>>("An unexpected error occurred: ${e.message}"))
        }
    }
}

