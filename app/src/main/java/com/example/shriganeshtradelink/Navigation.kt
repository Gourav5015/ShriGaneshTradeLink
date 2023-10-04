package com.example.shriganeshtradelink

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shriganeshtradelink.ViewModel.CartViewModel
import com.example.shriganeshtradelink.ViewModel.ItemsViewModel

@Composable
fun App( navController: NavHostController,
         itemViewModel:ItemsViewModel,
         cartViewModel: CartViewModel,
         ){
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        // Define your navigation graph here
        composable(Screen.HomeScreen.route) { HomeScreen(navController = navController ,itemViewModel,cartViewModel) }
        composable(Screen.CartScreen.route) { CartScreen(cartViewModel,navController)
        }
    }
}