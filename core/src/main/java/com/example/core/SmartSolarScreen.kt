package com.example.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.domain.SmartDetalleTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartSolarScreen(
//  viewModel: SmartSolarViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController,
    onFilterClick: () -> Unit
) {
    val tabNames = listOf("Mi instalación", "Energía", "Detalles")
    val pagerStates = rememberPagerState(initialPage = 0) {
        tabNames.size
    }
    val coroutineScope = rememberCoroutineScope()
//    val detalle = viewModel.detalle.collectAsState().value

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        verticalArrangement = Arrangement.Center,

                        ) {
                        Text(
                            text = stringResource(R.string.topBar_smart_solar),
                            color = colorResource(R.color.screen_fact_color),
                            fontSize = 18.sp,
                            modifier = Modifier.clickable{navController.popBackStack()}
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back",
                            tint = colorResource(R.color.screen_fact_color)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.White,
                    scrolledContainerColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }) { paddingScaffold ->

        Column(
            modifier = Modifier
                .padding(paddingScaffold)

        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, bottom = 24.dp),
                text = stringResource(R.string.title_smart_solar),
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.Black
            )
            val currentPage = pagerStates.currentPage
            TabRow(
                selectedTabIndex = currentPage,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentColor = Color.Black,
                containerColor = Color.White,
                indicator = { tabPositions ->
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[currentPage])
                            .height(2.dp)
                            .background(color = Color.Black)

                    )
                }

            ) {
                tabNames.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerStates.currentPage == index,
                        onClick = {
                            coroutineScope.launch { pagerStates.animateScrollToPage(index) }
                        },
                        text = {
                            Text(
                                text = title,
                                fontSize = 14.sp
                            )
                        }
                    )
                }
            }
            HorizontalPager(
                state = pagerStates,
                modifier = Modifier.fillMaxSize()

            ) { page ->
                when (page) {
                    0 -> TabMiInstalacionview()
                    1 -> TabEnergiaView()
                    2 -> TabDetallesView()/*{
                       *//* if (detalle == null) {
                            // Muestra un loading mientras se carga
                            Box(modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center)
                            {
                                CircularProgressIndicator()
                            }
                        } else {
                            TabDetallesView(detalle) // Pasa los datos cargados para mostrarlos en el Tab
                        }*//*
                    }*/
                }
            }
        }
    }

}

@Composable
fun InfoDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.title_info),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.detale_info),
                fontSize = 14.sp,
                color = Color.Black
            )
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            )
            {
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.width(250.dp),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White,
                        containerColor = colorResource(id = R.color.screen_fact_color)
                    )
                ) {
                    Text(text = stringResource(R.string.btn_info))

                }
            }
        }

    )

}

@Composable
fun TabDetallesView() {
    var showDialog by remember { mutableStateOf(false) } //visibilidad del popup
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    )
    {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.text_cau),
            color = Color.Gray,
            fontSize = 12.sp
        )
        TextField(
            value = "",
            onValueChange = { },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .height(22.dp),
            colors = textViewFieldColors()
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.text_estado_solicitud),
            color = Color.Gray,
            fontSize = 12.sp
        )
        TextField(
            value = "",
            onValueChange = { },
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth()
                .height(22.dp),
            colors = textViewFieldColors(),

            trailingIcon = {
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = stringResource(R.string.info),
                        tint = colorResource(R.color.icon_info_color),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.text_tipo),
            color = Color.Gray,
            fontSize = 12.sp
        )
        TextField(
            value = "",
            onValueChange = { },
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth()
                .height(22.dp),
            colors = textViewFieldColors()
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.text_compensacion),
            color = Color.Gray,
            fontSize = 12.sp
        )
        TextField(
            value = "",
            onValueChange = { },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .height(22.dp),
            colors = textViewFieldColors()
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.text_potencia),
            color = Color.Gray,
            fontSize = 12.sp
        )
        TextField(
            value = "",
            onValueChange = { },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .height(22.dp),
            colors = textViewFieldColors()

        )

    }
    if (showDialog) {
        InfoDialog(
            onDismiss = {showDialog = false}
        )
    }

}
@Composable
fun textViewFieldColors() = TextFieldDefaults.colors(
focusedIndicatorColor = Color.Black,
focusedContainerColor = Color.Transparent,
unfocusedContainerColor = Color.Transparent,
disabledContainerColor = Color.Transparent
)
@Composable
fun TabEnergiaView() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.plan_gestiones),
            contentDescription = stringResource(R.string.text_energia),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.detalle_energia),
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp, end = 36.dp),
            textAlign = TextAlign.Center
        )

    }

}
@Composable
fun TabMiInstalacionview() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    )
    {
        Text(
            text = stringResource(R.string.text_mi_instalacion),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.Start)
        {
            Text(
                text = stringResource(R.string.text_smart_solar),
                fontSize = 14.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = stringResource(R.string.porcentaje_smart_solar),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        Image(
            painter = painterResource(R.drawable.grafico_smart_solar),
            contentDescription = stringResource(R.string.image_mi_instalación),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )


    }

}

