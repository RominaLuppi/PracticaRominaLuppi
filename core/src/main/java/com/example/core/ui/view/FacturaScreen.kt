package com.example.core.ui.view


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.core.R
import com.example.core.ui.viewModel.FacturaViewModel
import com.example.core.ui.viewModel.SharedViewModel
import com.example.domain.Factura
import com.google.android.material.transition.MaterialSharedAxis.Axis
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.StrokeStyle
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacturaScreen(
    facturaViewModel: FacturaViewModel,
    sharedViewModel: SharedViewModel,
    navController: NavHostController,
    onFilterClick: () -> Unit

) {
    //al volver a cargar la pantalla se muestran todas las facturas
    LaunchedEffect(Unit) {
        facturaViewModel.resetFacturas()
    }
    var showDialog by remember { mutableStateOf(false) }
    val isLoading by facturaViewModel.isLoading.observeAsState(false)
    val facturasFiltradas by sharedViewModel.facturasFiltradas.observeAsState(emptyList())

    Scaffold(
        modifier = Modifier.background(Color.White),
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
                            modifier = Modifier.clickable { navController.popBackStack() }
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("HomeScreen")
                    }) {
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

            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth()
            ) {
                graficaLinearFacturas(facturas = facturaViewModel.factura.value ?: emptyList())
            }

            FacturasList(
                list = if (facturasFiltradas.isNotEmpty()) {
                    facturasFiltradas
                } else {
                    facturaViewModel.factura.value ?: emptyList()
                },
                facturaViewModel = facturaViewModel,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),

                onClick = {
                    showDialog = true
                }
            )
        }
    }
    //mostrar el dialogo cuando se hace click en una factura
    if (showDialog) {
        FacturaDialog(
            onDismiss = {
                showDialog = false
            }
        )
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun FacturasList(
    list: List<Factura>,
    facturaViewModel: FacturaViewModel,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 8.dp)
    ) {

        items(list) { factura ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable(onClick = onClick)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(horizontal = 0.dp, vertical = 2.dp)
                        .weight(1f)
                        .fillMaxHeight()
                )
                {
                    val fechaFormatter = facturaViewModel.formatearFecha(factura.fecha)
                    Text(
                        text = fechaFormatter.toString(),
                        color = Color.DarkGray
                    )
                    if (factura.descEstado == stringResource(R.string.pendientes)) {
                        Text(
                            text = factura.descEstado,
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterVertically)
                )
                {
                    Text(
                        text = factura.importeOrdenacion.toString() + "€",
                        color = Color.DarkGray
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(R.string.abrir_factura),
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
        modifier = Modifier.clip(shape = RoundedCornerShape(12.dp)),
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.title_dialog),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.message_dialog),
                fontSize = 14.sp,
                color = Color.Black,
            )
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.width(250.dp),
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

@Composable
fun graficaLinearFacturas(facturas: List<Factura>) {

    //se formatean las fechas y se ordenan
    val dateInputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dateOutputFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())

    val ejeX = facturas.map {
        val date = dateInputFormat.parse(it.fecha)
        dateOutputFormat.format(date ?: it.fecha)
    }

    val sortedFacturas = facturas.sortedBy { dateInputFormat.parse(it.fecha)?.time ?: 0L }
    val valoresImporte = sortedFacturas.map { it.importeOrdenacion }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 16.dp),
            data = remember {
                listOf(
                    Line(
                        label = "€",
                        values = valoresImporte,
                        color = SolidColor(Color.Gray),
                        drawStyle = DrawStyle.Stroke(
                            strokeStyle = StrokeStyle.Dashed(intervals = floatArrayOf(10f, 10f), phase = 15f)),
                        dotProperties = DotProperties(
                            enabled = true,
                            color = SolidColor(colorResource(R.color.screen_fact_color)),
                            strokeWidth = 4.dp,
                            radius = 7.dp,
                            strokeColor = SolidColor(colorResource(R.color.screen_fact_color)),

                            ),
                    )
                )
            },
            gridProperties = GridProperties.AxisProperties(),


        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ejeX.forEach { ejeX ->
                Text(text = ejeX, fontSize = 10.sp)

            }

        }
    }

}







