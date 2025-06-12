package com.lithium.truepost.ui

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.lithium.truepost.data.repository.CourseRepository
import com.lithium.truepost.truePostApplication
import com.lithium.truepost.ui.course.CourseViewModel
import com.lithium.truepost.ui.login.LoginViewModel
import com.lithium.truepost.ui.menu.MenuViewModel
import com.lithium.truepost.ui.quiz.FacebookQuizViewModel
import com.lithium.truepost.ui.quiz.XQuizViewModel
import com.lithium.truepost.ui.register.RegisterViewModel
import com.lithium.truepost.ui.session.SessionViewModel

object TruePostViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val app = truePostApplication()
            LoginViewModel(app)
        }
        initializer {
            val app = truePostApplication()
            RegisterViewModel(app)
        }
        initializer {
            val app = truePostApplication()
            val repo = CourseRepository(app.database.courseProgressDao())
            MenuViewModel(repo, app)
        }
        initializer {
            val app = truePostApplication()
            FacebookQuizViewModel(app = app, maxQuestions = 10)
        }
        initializer {
            val app = truePostApplication()
            XQuizViewModel(app = app, maxQuestions = 10)
        }
        initializer {
            val app = truePostApplication()
            SessionViewModel(app)
        }
        initializer {
            val app = truePostApplication()
            CourseViewModel(app)
        }
    }
}