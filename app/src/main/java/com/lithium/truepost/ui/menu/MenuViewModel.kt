package com.lithium.truepost.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lithium.truepost.data.model.CourseData
import com.lithium.truepost.data.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MenuUiState(
    val name: String = "Usuario",
    val bestScore: Int = 100,
    val courses: List<CourseData>,
)

class MenuViewModel(
    private val repository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MenuUiState?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val courses = repository.getCourses()
            _uiState.value = MenuUiState(
                name = "Eduardo",
                bestScore = 100,
                courses = courses,
            )
        }
    }

    fun completeCourse(courseId: String) {
        viewModelScope.launch {
            repository.markCourseCompleted(courseId)
            val updatedCourses = repository.getCourses()
            _uiState.value = _uiState.value?.copy(courses = updatedCourses)
        }
    }
}
