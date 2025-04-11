package com.example.core


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltrosScreen(
    viewModel: FacturaViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController
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
        )
        {
            Spacer(modifier.height(4.dp))
            Text(
                text = stringResource(R.string.subtitle_filtros),
                modifier = Modifier.padding(16.dp),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
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
                    CalendFechaDesde()
                }

                Column() {
                    Text(
                        text = stringResource(R.string.fecha_hasta),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                    CalendFechaHasta()
                }
            }
            Divider(
                modifier = Modifier.padding(start = 16.dp, top = 20.dp, end = 16.dp),
                color = Color.LightGray,
                thickness = 1.dp,
                startIndent = 0.dp
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(R.string.text_por_importe),
                modifier = Modifier.padding(top = 26.dp, start = 16.dp, bottom = 6.dp),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            SeleccionarImporte(importeMax = 300.0f)

            Divider(
                modifier = Modifier.padding(start = 16.dp, top = 30.dp, end = 16.dp),
                color = Color.LightGray,
                thickness = 1.dp,
                startIndent = 0.dp
            )
            SeleccionarEstado()

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AplicarFiltros()
                EliminarFiltros()
            }

        }
            }

}

@Composable
fun AplicarFiltros() {
    Button(
        modifier = Modifier.width(250.dp)
            .padding(bottom = 16.dp),
        onClick = {
            TODO()
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
fun EliminarFiltros(viewModel: FacturaViewModel = viewModel()) {
    Button(
        modifier = Modifier.width(250.dp),
        onClick = {
            viewModel.ResetearFechas()
            viewModel.ResetearSlider()
            viewModel.ResetearCheckBox()
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
fun SeleccionarEstado(viewModel: FacturaViewModel = viewModel()) {
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
            .padding(top = 12.dp, end = 16.dp, start = 16.dp)
    ) {

        Text(
            text = stringResource(R.string.text_por_estado),
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, bottom = 8.dp),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        checkedState.forEachIndexed { index, isChecked ->  //se maneja el estado de los checkbox de manera independiente

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            )
            {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        viewModel.SelectorEstado(index, it)
                    }
                )
                Text(
                    text = label[index],
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}
@Composable
fun CalendFechaDesde(viewModel: FacturaViewModel = viewModel()){
    val selectedDate = viewModel.fechaDesde
    var showDate by remember { mutableStateOf(false) }

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
                viewModel.ActualizarFechaDesde(it)
            },
            onDismiss = { showDate = false }
        )
    }
}

@Composable
fun CalendFechaHasta(
    modifier: Modifier = Modifier,
    viewModel: FacturaViewModel = viewModel()
    ) {
    val selectedDate = viewModel.fechaHasta
    var showDate by remember { mutableStateOf(false) }

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
            onDismiss = { showDate = false }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(onDateSelected: (Long?) -> Unit, onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) { Text(stringResource(R.string.aceptar)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancelar)) }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun SeleccionarImporte(importeMax: Float,
                       viewModel: FacturaViewModel = viewModel()) {

    val sliderPosition = viewModel.sliderPosition
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp))
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
                modifier = Modifier.padding(bottom = 4.dp, start = 8.dp),
                color = Color.LightGray,
            )
            Text(
                text = stringResource(R.string.max_slider),
                modifier = Modifier.padding(bottom = 4.dp, start = 8.dp),
                color = Color.LightGray,
            )
        }
        Slider(
            value = sliderPosition,
            onValueChange = { range ->
                viewModel.SelectorImporte(range.toInt().toFloat())
            },
            valueRange = 0f..importeMax,
            colors = SliderDefaults.colors(
                thumbColor = colorResource(R.color.screen_fact_color),   // Color de los "puntos" que se arrastran
                activeTrackColor = colorResource(R.color.screen_fact_color),  // Color de la línea entre los thumbs
                inactiveTrackColor = Color.LightGray, // Color de la línea antes y después del rango seleccionado
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

