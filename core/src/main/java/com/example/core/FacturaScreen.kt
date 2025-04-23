package com.example.core


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.domain.Factura


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacturaScreen(
    viewModel: FacturaViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController,
    onFilterClick: () -> Unit //para hacer clickeable el icono del filtro

) {

    var showDialog by remember { mutableStateOf(false) } //visibilidad del popup

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = stringResource(R.string.title_topBar),
                            color = colorResource(R.color.screen_fact_color),
                            fontSize = 18.sp,
                            modifier = Modifier.clickable{navController.popBackStack()}
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("HomeScreen")
                    }){
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back",
                            tint = colorResource(R.color.screen_fact_color)

                        )
                    }

                },
                actions = {
                    IconButton(onClick = onFilterClick) {
                        Icon(
                            painter = painterResource(R.drawable.filtericon),
                            contentDescription = stringResource(R.string.filter_description)
                        )
                    }
                }

            )

        }) { paddingScaffold ->

        Column(
            modifier = Modifier
                .padding(paddingScaffold) //para no tapar el TopAppBar

        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(R.string.title_factura),
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.Black
            )

            FacturasList(
                viewModel = viewModel,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                onClick = {
                    showDialog = true
                }
            )
        }
    }
    if (showDialog) {
        FacturaDialog(
            onDismiss = {
                showDialog = false
            }
        )
    }
}

@Composable
fun FacturasList(
    onClick: () -> Unit,
    modifier: Modifier,
    viewModel: FacturaViewModel
    ) {
    //val state =
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 8.dp)

    ) {

        items(viewModel.state.facturaList) { factura ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable(
                        onClick = onClick
                    )

            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier

                        .padding(horizontal = 0.dp, vertical = 2.dp)
                        .weight(1f) //ocupa el mayor ancho posible

                )
                {
                    Text(text = factura.fecha, color = Color.DarkGray)
                    Text(text = factura.estado, color = Color.Red)
                }
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterVertically)
                )
                {
                    Text(
                        text = factura.importe.toString() + "€",
                        color = Color.DarkGray
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Abrir Factura",
                        tint = Color.DarkGray,
                    )
                }
            }
            Divider(
                modifier = Modifier.padding(top = 2.dp, end = 16.dp),
                color = Color.Gray,
                thickness = 1.dp,
                startIndent = 0.dp
            )
        }
    }
    }

@Composable
fun FacturaDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(id = R.string.title_dialog),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black)
        },
        text = {
            Text(text = stringResource(id = R.string.message_dialog),
                fontSize = 20.sp,
                color = Color.Black)
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.White,
                    containerColor = colorResource(id = R.color.screen_fact_color)
                )
            ) {
                Text(text = stringResource(R.string.button_dialog))
            }
        }

    )

}







