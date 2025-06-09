package com.lithium.truepost

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.lithium.truepost.ui.navigation.TruePostNavHost

@Composable
fun TruePostApp(
    navController: NavHostController = rememberNavController(),
) {
    TruePostNavHost(navController = navController, modifier = Modifier.fillMaxSize())
}