package com.lithium.truepost.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lithium.truepost.ui.home.HomeDestination
import com.lithium.truepost.ui.home.HomeScreen
import com.lithium.truepost.ui.login.LoginDestination
import com.lithium.truepost.ui.login.LoginScreen
import com.lithium.truepost.ui.menu.MenuDestination
import com.lithium.truepost.ui.menu.MenuScreen
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
        //composable(route = MenuDestination) {
          //  MenuScreen()
        //}
    }
}