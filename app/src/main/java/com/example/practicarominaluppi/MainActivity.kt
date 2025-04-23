package com.example.practicarominaluppi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.FacturaScreen
import com.example.core.FacturaViewModel
import com.example.core.FiltroViewModel
import com.example.core.FiltrosScreen
import com.example.core.HomeScreen
import com.example.core.SmartSolarScreen
import com.example.practicarominaluppi.ui.theme.PracticaRominaLuppiTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            PracticaRominaLuppiTheme {

                MainNavigation()
            }
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val facturaViewModel: FacturaViewModel = viewModel()
    val filtroViewModel: FiltroViewModel = viewModel()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "HomeScreen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("HomeScreen") {
                HomeScreen(navController)

            }
            composable("FacturaScreen") {
                FacturaScreen(
                    viewModel = facturaViewModel,
                    onFilterClick = { navController.navigate("FiltroScreen")},
                    navController = navController
                )
            }
            composable("FiltroScreen") {
                FiltrosScreen(
                   viewModel = filtroViewModel,
                   navController = navController
                )
            }
            composable("SmartSolarScreen") { backStackEntry ->
//                val viewModel: SmartSolarViewModel = hiltViewModel(backStackEntry)
                SmartSolarScreen(
                    modifier = Modifier,
                    contentPadding = PaddingValues(),
                    navController = navController,
                    onFilterClick = { },
//                    viewModel = TODO,
                )
            }
        }
    }
}


