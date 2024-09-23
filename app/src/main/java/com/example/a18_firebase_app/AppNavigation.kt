package com.example.a18_firebase_app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.a18_firebase_app.auth.sign_in.LoginPage
import com.example.a18_firebase_app.auth.sign_up.SignupPage
import com.example.a18_firebase_app.ui.pages.HomePage

@Composable
fun AppNavigation(authViewModel: AuthViewModel, navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = "login") {
        composable("login") { LoginPage(navHostController, authViewModel) }
        composable("signup") { SignupPage(navHostController, authViewModel) }
        composable("home") { HomePage(navHostController, authViewModel) }
    }
}

