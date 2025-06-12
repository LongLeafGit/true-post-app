package com.lithium.truepost.ui.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lithium.truepost.TruePostApplication
import com.lithium.truepost.data.local.CourseProgressEntity
import com.lithium.truepost.data.model.CourseContent
import com.lithium.truepost.data.raw.AllCourses
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CourseUiData(
    val id: Int = 0,
    val imageResId: Int = 0,
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false,
    val content: List<CourseContent> = emptyList()
)

class CourseViewModel(
    private val app: TruePostApplication
) : ViewModel() {

    private val _uiState = MutableStateFlow(CourseUiData())
    val uiState = _uiState.asStateFlow()

    fun loadCourse(courseId: Int) {
        viewModelScope.launch {
            val course = AllCourses.find { it.id == courseId }
            val progress = app.database.courseProgressDao().getProgress(courseId)
            course?.let {
                _uiState.value = CourseUiData(
                    id = it.id,
                    imageResId = it.imageResId,
                    title = it.title,
                    description = it.description,
                    completed = progress?.completed ?: false,
                    content = it.content
                )
            }
        }
    }

    fun markCompleted() {
        val courseId = _uiState.value.id
        viewModelScope.launch {
            app.database.courseProgressDao().insertOrUpdate(
                CourseProgressEntity(courseId = courseId, completed = true)
            )
            _uiState.value = _uiState.value.copy(completed = true)
        }
    }
}