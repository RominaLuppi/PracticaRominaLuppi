package com.example.practicarominaluppi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.FacturaScreen
import com.example.core.FacturaViewModel
import com.example.core.FiltrosScreen
import com.example.practicarominaluppi.ui.theme.PracticaRominaLuppiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            PracticaRominaLuppiTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FacturaScreen(
                        modifier = Modifier.padding(innerPadding),
                        onFilterClick = {},
                        viewModel = FacturaViewModel()
                    )
                }
            }
            Navegacion()
        }
    }
}
@Composable
fun Navegacion(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "FacturaScreen"){
        composable("FacturaScreen") {
            FacturaScreen(
                viewModel = viewModel(),
                onFilterClick = { navController.navigate("FiltroScreen")}
            )
        }
        composable("FiltroScreen") {
            FiltrosScreen(
                viewModel = viewModel(),
                navController = navController)
        }
    }
}

