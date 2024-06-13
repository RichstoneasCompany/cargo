package com.example.richstonecargo.domain.use_case.questions

import android.util.Log
import com.example.richstonecargo.common.Resource
import com.example.richstonecargo.domain.model.Question
import com.example.richstonecargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class QuestionsUseCase @Inject constructor(
    private val repository: CargoRepository
) {
    operator fun invoke(id: Long): Flow<Resource<List<Question>>> = flow {
        try {
            emit(Resource.Loading<List<Question>>())
            val questionsList = repository.getQuestionsByTopicId(id)
            Log.d("QuestionsUseCase", "Questions: $questionsList")
            emit(Resource.Success<List<Question>>(questionsList))
        } catch (e: HttpException) {
            Log.e("QuestionsUseCase", "HTTP exception: ${e.message}")
            emit(
                Resource.Error<List<Question>>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            Log.e("QuestionsUseCase", "Network exception: ${e.message}")
            emit(Resource.Error<List<Question>>("Couldn't reach server. Check your internet connection."))
        }
    }
}
