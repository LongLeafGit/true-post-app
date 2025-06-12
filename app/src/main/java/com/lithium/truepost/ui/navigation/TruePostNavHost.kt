package com.lithium.truepost.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lithium.truepost.ui.course.CourseScreen
import com.lithium.truepost.ui.home.HomeDestination
import com.lithium.truepost.ui.home.HomeScreen
import com.lithium.truepost.ui.login.LoginDestination
import com.lithium.truepost.ui.login.LoginScreen
import com.lithium.truepost.ui.menu.MenuDestination
import com.lithium.truepost.ui.menu.MenuScreen
import com.lithium.truepost.ui.quiz.FacebookDestination
import com.lithium.truepost.ui.quiz.FacebookQuizScreen
import com.lithium.truepost.ui.quiz.XDestination
import com.lithium.truepost.ui.quiz.XQuizScreen
import com.lithium.truepost.ui.register.RegisterDestination
import com.lithium.truepost.ui.register.RegisterScreen

@Composable
fun TruePostNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination,
        modifier = modifier,
    ) {
        composable(route = HomeDestination) {
            HomeScreen(
                onRegisterClick = { navController.navigate(RegisterDestination) },
                onLoginClick = { navController.navigate(LoginDestination) },
                onSessionActive = { navController.navigate(MenuDestination) },
            )
        }
        composable(route = LoginDestination) {
            LoginScreen(
                onLoginClick = { navController.navigate(MenuDestination) },
                onRegisterClick = { navController.navigate(RegisterDestination) },
            )
        }
        composable(route = RegisterDestination) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(LoginDestination) },
                onBackToLogin = { navController.navigate(LoginDestination) }
            )
        }
        composable(route = MenuDestination) {
            MenuScreen(
                onCourseClick = { courseId -> navController.navigate("course/$courseId") },
                onFacebookQuizClick = { navController.navigate(FacebookDestination) },
                onXQuizClick = { navController.navigate(XDestination) },
                onExitClick = { navController.navigate(HomeDestination) }
            )
        }
        composable("course/{courseId}") { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId")?.toIntOrNull()
            if (courseId != null) {
                CourseScreen(
                    courseId = courseId,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
        composable(route = FacebookDestination) {
            FacebookQuizScreen(
                onBackToMenu = { navController.navigateUp() }
            )
        }
        composable(route = XDestination) {
            XQuizScreen(
                onExitClick = { navController.navigateUp() }
            )
        }
    }
}