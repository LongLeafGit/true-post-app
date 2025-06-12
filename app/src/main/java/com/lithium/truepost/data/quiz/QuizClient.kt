package com.lithium.truepost.data.quiz

import com.lithium.truepost.BuildConfig
import com.lithium.truepost.data.auth.AuthClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.Serializable

@Serializable
data class QuizResult(
    val user_id: String,
    val user_name: String,
    val points: Int,
    val correct: Int,
    val incorrect: Int,
    val total_time: Int,
    val quiz_type: String,
    val finished_at: String,
    val questions_telemetry: List<QuestionTelemetry>
)

@Serializable
data class QuestionTelemetry(
    val postId: String,
    val userAnsweredLegit: Boolean,
    val wasCorrect: Boolean,
    val responseTime: Int,
    val timestamp: String,
)

object QuizClient {
    private val client get() = AuthClient.client

    suspend fun sendQuizResult(
        userId: String,
        username: String,
        points: Int,
        correct: Int,
        incorrect: Int,
        totalTime: Int,
        quizType: String,
        finishedAt: String,
        questionsTelemetry: List<QuestionTelemetry>
    ) {
        try {
            val result = QuizResult(
                user_id = userId,
                user_name = username,
                points = points,
                correct = correct,
                incorrect = incorrect,
                total_time = totalTime,
                quiz_type = quizType,
                finished_at = finishedAt,
                questions_telemetry = questionsTelemetry
            )
            client.postgrest["quiz_results"].insert(result)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
