package com.example.core.ui.view


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.core.R
import com.example.core.ui.viewModel.FacturaViewModel
import com.example.core.ui.viewModel.SharedViewModel
import com.example.domain.Factura
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.GridProperties.AxisProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.LineProperties
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

    var isEuroMode by remember { mutableStateOf(false) }

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
                    IconButton(onClick = onFilterClick, modifier = Modifier.padding(end = 6.dp)) {
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
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                text = stringResource(R.string.title_factura),
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.Black
            )
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                SwitchEuroKwh(isEuroMode = isEuroMode, onModeChange = { isEuroMode = it })

                Spacer(modifier = Modifier.height(8.dp))

                if (!isEuroMode) GraficaLinearFacturas(
                    facturas = facturaViewModel.factura.value ?: emptyList()
                )
                else GraficaBarrasFacturas(facturas = facturaViewModel.factura.value ?: emptyList())
            }


            Spacer(modifier = Modifier.height(8.dp))

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
    //muestra el dialog cuando se hace clic en una factura
    if (showDialog) {
        FacturaDialog(
            onDismiss = { showDialog = false }
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
                    .height(68.dp)
                    .padding(horizontal = 16.dp, vertical = 10.dp)
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
                            fontSize = 13.sp
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
fun GraficaLinearFacturas(facturas: List<Factura>) {

    //se formatean las fechas y se ordenan
    val dateInputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dateOutputFormat = SimpleDateFormat("MMM yy", Locale.getDefault())

    val sortedFacturas = facturas.sortedBy { dateInputFormat.parse(it.fecha)?.time ?: 0L }

    val ejeX = sortedFacturas.map {factura ->
        val date = dateInputFormat.parse(factura.fecha)
        dateOutputFormat.format(date ?: factura.fecha)
    }
    val ejeY = sortedFacturas.map { it.importeOrdenacion }
    val color = colorResource(R.color.screen_fact_color)
    val colorFill = colorResource(R.color.linear_color)

    // Configuración para valores del eje Y sobre lineas divider
    val minY: Double = ejeY.minOrNull() ?: 0.0
    val maxY: Double = ejeY.maxOrNull() ?: 0.0
    val steps = 3
    val stepValue = (maxY - minY) / ((steps - 1).coerceAtLeast(1).toDouble())

    val yLabels = List(steps) { i -> String.format("%.0f €", maxY - i * stepValue) }

    Column(modifier = Modifier.padding(horizontal = 14.dp)) {
        Row(modifier = Modifier.height(200.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                LineChart(
                    labelProperties = LabelProperties(enabled = false),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 12.dp),
                    data =
                        listOf(
                            Line(
                                label = "€/mes",
                                values = ejeY,
                                color = SolidColor(color),
                                firstGradientFillColor = colorFill,
                                dotProperties = DotProperties(
                                    enabled = true,
                                    color = SolidColor(color),
                                    radius = 2.dp,
                                    strokeColor = SolidColor(color),
                                ),
                                curvedEdges = false,
                                gradientAnimationDelay = 400
                            ),
                        ),
                    gridProperties = GridProperties(
                        xAxisProperties = AxisProperties(
                            color = SolidColor(Color.LightGray),
                            thickness = 1.dp,
                            enabled = true,
                            lineCount = steps,
                        ),
                        yAxisProperties = AxisProperties(enabled = false)
                    ),
                    dividerProperties = DividerProperties(
                        xAxisProperties = LineProperties(
                            color = SolidColor(Color.LightGray),
                            thickness = 1.dp,
                            enabled = true
                        ),
                    )
                )
            }
            // valores del ejeY
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 20.dp, top = 14.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                yLabels.forEach { label ->
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.End
                    )
                }
            }

        }
        // valores ejeX
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 46.dp, end = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),

            ) {
            ejeX.take(6).forEachIndexed { index, label ->
                Text(
                    text = label,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun GraficaBarrasFacturas(facturas: List<Factura>) {
    //se formatean las fechas y se ordenan
    val dateInputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dateOutputFormat = SimpleDateFormat("MMM yy", Locale.getDefault())
    val sortedFacturas = facturas.sortedBy { dateInputFormat.parse(it.fecha)?.time ?: 0L }

    val color = colorResource(R.color.screen_fact_color)

    val ejeX = sortedFacturas.map { factura ->
        val date = dateInputFormat.parse(factura.fecha)
        val mesFormat = date?.let { dateOutputFormat.format(it) } ?: "fecha inválida"


            Bars(
            label = mesFormat.toString(),
            values = listOf(
                Bars.Data(
                    value = factura.kwh,
                    color = SolidColor(color)
                )
            )
        )
    }
    val showEjeX = ejeX.take(5)
    Box(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        ColumnChart(
            modifier = Modifier.heightIn(min = 150.dp, max = 250.dp)
                .padding(end = 20.dp, top = 14.dp),
            data = showEjeX,
            barProperties = BarProperties(
                spacing = 8.dp,
                thickness = 20.dp,
                style = DrawStyle.Fill
            ),
            gridProperties = GridProperties(
                xAxisProperties = AxisProperties(
                    color = SolidColor(Color.LightGray),
                    thickness = 1.dp,
                    enabled = true,
                ),
                yAxisProperties = AxisProperties(enabled = false)
            ),
        )
    }

}

@Composable
fun SwitchEuroKwh(isEuroMode: Boolean, onModeChange: (Boolean) -> Unit)
{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "€", fontSize = 12.sp, modifier = Modifier)

            Switch(
                modifier = Modifier,
                enabled = true,
                checked = isEuroMode,
                onCheckedChange = { onModeChange (it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = colorResource(R.color.screen_fact_color),
                    uncheckedTrackColor = Color.LightGray
                )
            )
            Text(text = "kWh", fontSize = 12.sp, modifier = Modifier)
    }

}












