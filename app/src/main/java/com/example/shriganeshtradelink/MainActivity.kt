package com.example.shriganeshtradelink
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shriganeshtradelink.ViewModel.CartViewModel
import com.example.shriganeshtradelink.ViewModel.ItemsViewModel
import com.example.shriganeshtradelink.ui.theme.ShriGaneshTradeLinkTheme

class MainActivity : ComponentActivity(){
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var itemViewModel= ItemsViewModel()
        var cartViewModel= CartViewModel()
        setContent {
            ShriGaneshTradeLinkTheme {
                val navController = rememberNavController()
                App(navController =navController ,itemViewModel,cartViewModel)
            }
        }
    }
}

