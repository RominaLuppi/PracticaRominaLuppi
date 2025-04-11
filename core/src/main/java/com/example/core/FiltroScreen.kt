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
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Column {
                    Text(
                        text = stringResource(R.string.fecha_desde),
                        modifier = Modifier.padding(16.dp),
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                    CalendarioFechas()
                }
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = stringResource(R.string.fecha_hasta),
                        modifier = Modifier.padding(16.dp),
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                    CalendarioFechas()
                }

            }
            Divider(
                modifier = Modifier.padding(start = 16.dp, top = 30.dp, end = 16.dp),
                color = Color.LightGray,
                thickness = 1.dp,
                startIndent = 0.dp
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(R.string.text_filtos),
                modifier = Modifier.padding(top = 30.dp, start = 16.dp, bottom = 24.dp),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Slider(
                importeMax = 25.90f
            )

            Divider(
                modifier = Modifier.padding(start = 16.dp, top = 30.dp, end = 16.dp),
                color = Color.LightGray,
                thickness = 1.dp,
                startIndent = 0.dp
            )

        }


    }
    Row {

    }
}

@Composable
fun CalendarioFechas(modifier: Modifier = Modifier) {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showDate by remember { mutableStateOf(false) }
    //var showDateHasta by remember { mutableStateOf(false) }

    Button(
        onClick = { showDate = true },
        modifier = Modifier,
        colors = ButtonDefaults.buttonColors(Color.LightGray)
    ) {
        Text(
            text = stringResource(R.string.btn_fecha),
            color = Color.Black
        )
    }
//    Button(onClick = {showDateHasta = true }) {
//        Text(text = stringResource(R.string.btn_fecha))
//    }

    if (showDate) {
        DatePickerModal(
            onDateSelected = { selectedDate = it },
            onDismiss = { showDate = false }
        )
    }
//    if (showDateHasta){
//        DatePickerModal(
//            onDateSelected = { selectedDate = it},
//            onDismiss = { showDateHasta = false}
//        )
//    }
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

fun convertMillisToDate(millis: Long): String? {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Composable
fun Slider(importeMax: Float) {
    var sliderPosition by remember { mutableStateOf(0f) }
    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = "${sliderPosition.toInt()}€",
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )
        Slider(
            value = sliderPosition,
            onValueChange = { range ->
                sliderPosition = range.toInt().toFloat() },
            valueRange = 0f..importeMax,
            //steps = (importeMax - 1).toInt(),
            colors = SliderDefaults.colors(
                thumbColor = colorResource(R.color.screen_fact_color),   // Color de los "puntos" que se arrastran
                activeTrackColor = colorResource(R.color.screen_fact_color),  // Color de la línea entre los thumbs
                inactiveTrackColor = Color.LightGray, // Color de la línea antes y después del rango seleccionado
            ),
            modifier = Modifier.fillMaxWidth(),
        )


    }
}

