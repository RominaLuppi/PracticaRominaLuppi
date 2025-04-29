package com.example.core.ui.viewModel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.core.R
import com.example.core.ui.view.FacturaFiltroState
import java.text.SimpleDateFormat
import java.util.Date


class FiltroViewModel: ViewModel() {


    var fechaDesde by mutableStateOf<Long?>(null)
        private set

    var fechaHasta by mutableStateOf<Long?>(null)
        private set

    var sliderPosition by mutableStateOf(0f)
        private set

    var checkedState by mutableStateOf(List(5) { false })
        private set

    fun ActualizarFechaDesde(date: Long?){
        fechaDesde = date
    }
    fun ActualizarFechaHasta(date: Long?){
        fechaHasta = date
    }

    fun SelectorImporte(value: Float){
        sliderPosition = value
    }

    fun SelectorEstado(index: Int, value: Boolean){
        checkedState = checkedState.toMutableList().apply { this[index] = value }

    }
    fun ResetearFechas(){
        fechaDesde = null
        fechaHasta = null
    }

    fun ResetearSlider(){
        sliderPosition = 0f
    }

    fun ResetearCheckBox(){
        checkedState = List(5) {false}
    }

    fun ConstruirFiltroState(): FacturaFiltroState {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        val fechaDesdeState = fechaDesde?.let { formatoFecha.format(Date(it)) } ?: ""
        val fechaHastaState = fechaHasta?.let { formatoFecha.format(Date(it)) } ?: ""

        val estadosDisponibles = listOf("pagadas", "anuladas", "cuota fija", "pendientes de pago", "plan de pago")
        val estadosSeleccionados = estadosDisponibles.filterIndexed { index, _ -> checkedState[index] }

        return FacturaFiltroState(
            fechaDesde = fechaDesdeState,
            fechaHasta = fechaHastaState,
            importeMin = 0.0,
            importeMax = sliderPosition.toDouble(),
            estado = estadosSeleccionados,
            succes = true
        )


    }


}