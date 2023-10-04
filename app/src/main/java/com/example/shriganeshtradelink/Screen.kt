package com.example.shriganeshtradelink

sealed class Screen( val route:String){
    object HomeScreen: Screen("home")
    object CartScreen:Screen("cart")
}
