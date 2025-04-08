package com.example.practicarominaluppi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.FacturaScreen
import com.example.domain.Factura
import com.example.practicarominaluppi.ui.theme.PracticaRominaLuppiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticaRominaLuppiTheme {

                //se crea una factura ficticia
//                val facturas = listOf(
//                    Factura(estado = "pagada", importe = 100.0, fecha = "21/08/2000"),
//                    Factura(estado = "pagada", importe = 130.0, fecha = "21/10/2000"),
//                    Factura(estado = "pendiente", importe = 5000.0, fecha = "01/08/2000")
//                )
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FacturaScreen(

                        onFilterClick = {},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

