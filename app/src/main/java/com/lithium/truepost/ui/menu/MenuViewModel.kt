package com.lithium.truepost.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lithium.truepost.TruePostApplication
import com.lithium.truepost.data.model.CourseData
import com.lithium.truepost.data.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MenuUiState(
    val name: String = "Usuario",
    val bestScore: Int = 0,
    val courses: List<CourseData>,
)

class MenuViewModel(
    private val repository: CourseRepository,
    private val app: TruePostApplication,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MenuUiState>(MenuUiState(courses = emptyList()))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val courses = repository.getCourses()
            val user = app.auth.getCurrentUser()
            val email = user?.email

            val userEntity = email?.let { app.database.userDao().getByEmail(it) }

            _uiState.value = MenuUiState(
                name = userEntity?.firstname ?: "Usuario",
                bestScore = userEntity?.bestScore ?: 0,
                courses = courses,
            )
        }
    }
}
