package com.example.core.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.core.R
import com.example.core.ui.viewModel.FacturaViewModel
import com.example.data.di.MockConfig
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    facturaViewModel: FacturaViewModel
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.background(Color.White),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                title = {
                    Text(
                        text = stringResource(R.string.title_inicio),
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingScaffold ->

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingScaffold),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Divider(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                color = Color.Gray,
                thickness = 1.5.dp,
                startIndent = 0.dp
            )
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {

                val colorIcono = remember { mutableStateOf(MockConfig.mockActive) }
                IconButton(
                    onClick = {
                        MockConfig.mockActive = !MockConfig.mockActive

                        //para mostrar el mensaje con snackbar
                        scope.launch {
                            //si esta el mock activado primero se borra la base de datos
                            facturaViewModel.clearAllDatabase()

                            //y luego se informa al usuario y se cargan las facturas
                            if (MockConfig.mockActive) {
                                colorIcono.value = true
                                snackbarHostState.showSnackbar("Modo mock activado")
                            } else {
                                colorIcono.value = false
                                snackbarHostState.showSnackbar("Modo mock desactivado")
                            }
                            facturaViewModel.cargarFacturas()
                        }
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (colorIcono.value)
                            colorResource(R.color.screen_fact_color)
                        else
                            colorResource(R.color.debug_icon_color),
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.debug_icon),
                        contentDescription = stringResource(R.string.btn_activar_debug)
                    )
                }
            }
            Spacer(modifier = Modifier.height(28.dp))
            Image(
                painter = painterResource(R.drawable.iberdrola_logo),
                contentDescription = stringResource(R.string.logo_iberdrola),
                modifier = Modifier
                    .size(350.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.height(36.dp))

            Column (
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.Center
            )
            {
                Button(
                    modifier = Modifier.width(350.dp),
                    onClick = {
                        navController.navigate("FacturaScreen")
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.screen_fact_color))
                ) {
                    Text(
                        text = stringResource(R.string.btn_home_facturas),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    modifier = Modifier.width(350.dp),
                    onClick = {
                        navController.navigate("SmartSolarScreen")
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.screen_fact_color))
                ) {
                    Text(
                        text = stringResource(R.string.btn_home_smart_solar),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}