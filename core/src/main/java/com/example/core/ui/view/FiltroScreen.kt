package com.example.core.ui.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.core.R
import com.example.core.ui.viewModel.FacturaViewModel
import com.example.core.ui.viewModel.FiltroViewModel
import com.example.core.ui.viewModel.SharedViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltrosScreen(
    filtroViewModel: FiltroViewModel,
    facturaViewModel: FacturaViewModel,
    sharedViewModel: SharedViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.title_filtros),
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                },
                actions = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.close_icon),
                            contentDescription = stringResource(R.string.close_button)
                        )
                    }
                }
            )
        }) { paddingScaffold ->

        Column(
            modifier = Modifier
                .padding(paddingScaffold)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        )
        {

            Text(
                text = stringResource(R.string.subtitle_filtros),
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start
            )
            {
                Column(modifier = Modifier.padding(end = 20.dp)) {
                    Text(
                        text = stringResource(R.string.fecha_desde),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                    CalendFechaDesde(filtroViewModel)
                }

                Column() {
                    Text(
                        text = stringResource(R.string.fecha_hasta),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                    CalendFechaHasta(filtroViewModel)
                }
            }
            Divider(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                color = Color.LightGray,
                thickness = 1.dp,
                startIndent = 0.dp
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(R.string.text_por_importe),
                modifier = Modifier.padding(top = 26.dp, start = 16.dp, bottom = 4.dp),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            SeleccionarImporte(filtroViewModel, facturaViewModel)

            Divider(
                modifier = Modifier.padding(start = 16.dp, top = 30.dp, end = 16.dp),
                color = Color.LightGray,
                thickness = 1.dp,
                startIndent = 0.dp
            )
            SeleccionarEstado(filtroViewModel)
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AplicarFiltros(filtroViewModel, facturaViewModel, navController)
                EliminarFiltros(filtroViewModel)

            }

        }
    }
}

@Composable
fun AplicarFiltros(
    filtroViewModel: FiltroViewModel,
    facturaViewModel: FacturaViewModel,
//    sharedViewModel: SharedViewModel,
    navController: NavHostController
) {

    var showDialog by remember { mutableStateOf(false) }
    val facturasOrig by facturaViewModel.factura.observeAsState()
    val filtro by filtroViewModel.filtro.observeAsState()

    val context = LocalContext.current
    val errorMsg by filtroViewModel.errorMsg.collectAsState()

    LaunchedEffect(errorMsg) {
        errorMsg?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            filtroViewModel.limpiarMsgError()
        }
    }

    if (showDialog) {
        ShowDialog(onDismiss = { showDialog = false })
    }

    Button(
        modifier = Modifier
            .width(250.dp)
            .padding(bottom = 14.dp),
        onClick = {
            //comprobar validez de las fechas
            val fechaDesde = filtroViewModel.fechaDesde
            val fechaHasta = filtroViewModel.fechaHasta

            if (fechaDesde != null && fechaHasta != null && fechaDesde > fechaHasta) {
                filtroViewModel.mostrarMsgError("El rango de fechas no es válido")
                return@Button
            }

            //se construye el filtro
            val filtroActual = filtroViewModel.ConstruirFiltroState()

            //se actualiza el filtro en el viewModel
            filtroViewModel.actualizarFiltro(filtroActual)

            //se filtran las facturas
            val facturasFiltradas =
                facturaViewModel.filtrarFacturas(facturasOrig ?: emptyList(), filtroActual)

            //si no hay facturas por mostrar se muestra el Dialog
            if (facturasFiltradas.isEmpty()) {
                showDialog = true
            } else {
                //se actualiza la lista de facturas
                facturaViewModel.actualizarFacturas(facturasFiltradas)

                navController.popBackStack()

            }

        },
        colors = ButtonDefaults.buttonColors(colorResource(R.color.screen_fact_color))
    ) {
        Text(
            text = stringResource(R.string.btn_aplicar),
            color = Color.White
        )
    }
}

@Composable
fun ShowDialog(onDismiss: () -> Unit) {
    AlertDialog(
        modifier = Modifier
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(12.dp)),

        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.title_dialog_filtro),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.text_dialog_filtro),
                fontSize = 14.sp,
                color = Color.Black
            )
        },
        confirmButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
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
fun EliminarFiltros(viewModel: FiltroViewModel) {
    Button(
        modifier = Modifier.width(250.dp),
        onClick = {
            viewModel.ResetarFiltros()

        },
        colors = ButtonDefaults.buttonColors(Color.Gray)
    ) {
        Text(
            text = stringResource(R.string.btn_eliminar_filtros),
            color = Color.White
        )
    }

}

@Composable
fun SeleccionarEstado(viewModel: FiltroViewModel) {
    val checkedState = viewModel.checkedState
    val label = listOf(
        stringResource(R.string.pagadas),
        stringResource(R.string.anuladas),
        stringResource(R.string.cuota_fija),
        stringResource(R.string.pendientes),
        stringResource(R.string.plan_pago)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, end = 16.dp, start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(0.1.dp)
    ) {

        Text(
            text = stringResource(R.string.text_por_estado),
            modifier = Modifier.padding(top = 6.dp, start = 16.dp, bottom = 6.dp),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        checkedState.forEachIndexed { index, isChecked ->  //se maneja el estado de los checkbox de manera independiente

            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            )
            {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        viewModel.SelectorEstado(index, it)
                    },
                    colors = androidx.compose.material.CheckboxDefaults.colors(
                        checkedColor = colorResource(R.color.screen_fact_color),
                        uncheckedColor = Color.Gray
                    )
                )
                Text(
                    text = label[index],
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable {
                            viewModel.SelectorEstado(
                                index,
                                !isChecked
                            )
                        } //para hacer el texto clickeable tambien
                )
            }
        }
    }
}

@Composable
fun CalendFechaDesde(viewModel: FiltroViewModel) {
    val selectedDate = viewModel.fechaDesde
    var showDate by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val errorMsgDesde by viewModel.errorMsgDesde.collectAsState()

    LaunchedEffect(errorMsgDesde) {
        errorMsgDesde?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.limpiarMsgErrorFechaDesde()
        }
    }
    val formattedDate = selectedDate?.let {
        it
        val date = Date(it)
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
    } ?: stringResource(R.string.btn_fecha)

    Button(
        onClick = { showDate = true },
        modifier = Modifier,
        colors = ButtonDefaults.buttonColors(colorResource(R.color.btn_fecha_color)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = formattedDate,
            color = Color.Black
        )
    }
    if (showDate) {
        DatePickerModal(
            onDateSelected = {
                viewModel.ActualizarFechaDesde(it)
            },
            onDismiss = { showDate = false },
            title = {
                Text(
                    stringResource(R.string.title_date_desde),
                    modifier = Modifier.padding(16.dp)
                )
            }
        )
    }
}
@Composable
fun CalendFechaHasta(
    viewModel: FiltroViewModel
) {
    val selectedDate = viewModel.fechaHasta
    var showDate by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val errorMsgHasta by viewModel.errorMsgHasta.collectAsState()

    LaunchedEffect(errorMsgHasta) {
        errorMsgHasta?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.limpiarMsgErrorFechaHasta()
        }
    }
    val formattedDate = selectedDate?.let {
        val date = Date(it)
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
    } ?: stringResource(R.string.btn_fecha)

    Button(
        onClick = { showDate = true },
        modifier = Modifier,
        colors = ButtonDefaults.buttonColors(colorResource(R.color.btn_fecha_color)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = formattedDate,
            color = Color.Black
        )
    }
    if (showDate) {
        DatePickerModal(
            onDateSelected = {
                viewModel.ActualizarFechaHasta(it)
            },
            onDismiss = { showDate = false },
            title = {
                Text(
                    stringResource(R.string.title_date_hasta),
                    modifier = Modifier.padding(16.dp)
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit, onDismiss: () -> Unit,
    title: (@Composable () -> Unit)? = null
) {
    val datePickerState = rememberDatePickerState()
    val datePickerColors = DatePickerDefaults.colors(
        containerColor = Color.White,
        dayContentColor = Color.Black,
        disabledDayContentColor = Color.Gray,
        todayContentColor = Color.Black,
        selectedDayContainerColor = colorResource(R.color.screen_fact_color),
        todayDateBorderColor = colorResource(R.color.screen_fact_color)
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }
            ) {
                Text(
                    stringResource(R.string.aceptar),
                    color = colorResource(R.color.screen_fact_color)
                )
            }

        },
        dismissButton = {
            TextButton(onClick = onDismiss)

            {
                Text(
                    stringResource(R.string.cancelar),
                    color = colorResource(R.color.screen_fact_color)
                )
            }
        },
        colors = datePickerColors

    ) {
        DatePicker(
            state = datePickerState,
            colors = datePickerColors,
            title = { title?.invoke() }

        )
    }
}

@Composable
fun SeleccionarImporte(
    filtroViewModel: FiltroViewModel,
    facturaViewModel: FacturaViewModel
) {

    val sliderPosition = filtroViewModel.sliderPosition
    val importeMax by facturaViewModel.maxImporte.observeAsState(0)
    val impMax = importeMax.toFloat()

    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
    {

        Text(
            text = "${sliderPosition.toInt()}€",
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            color = colorResource(R.color.screen_fact_color)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.min_slider),
                modifier = Modifier.padding(start = 8.dp),
                color = Color.LightGray,
            )
            Text(
                text = impMax.toString(),
                modifier = Modifier.padding(start = 8.dp),
                color = Color.LightGray,
            )
        }
        Slider(
            value = sliderPosition,
            onValueChange = { range ->
                filtroViewModel.SelectorImporte(range.toInt().toFloat())
            },
            valueRange = 0f..impMax,
            colors = SliderDefaults.colors(
                thumbColor = colorResource(R.color.screen_fact_color),   // Color de los "puntos" que se arrastran
                activeTrackColor = colorResource(R.color.screen_fact_color),  // Color de la línea entre los thumbs
                inactiveTrackColor = Color.LightGray, // Color de la línea antes y después del rango seleccionado
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

