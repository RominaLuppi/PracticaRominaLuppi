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
import com.example.core.ui.view.FacturaScreen
import com.example.core.ui.viewModel.FacturaViewModel
import com.example.core.ui.viewModel.FiltroViewModel
import com.example.core.ui.view.FiltrosScreen
import com.example.core.ui.view.HomeScreen
import com.example.core.ui.view.SmartSolarScreen
import com.example.practicarominaluppi.ui.theme.PracticaRominaLuppiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                    facturaViewModel = facturaViewModel,
                    onFilterClick = { navController.navigate("FiltroScreen") },
                    navController = navController
                )
            }
            composable("FiltroScreen") {
                FiltrosScreen(
                    filtroViewModel = filtroViewModel,
                    facturaViewModel = facturaViewModel,
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


