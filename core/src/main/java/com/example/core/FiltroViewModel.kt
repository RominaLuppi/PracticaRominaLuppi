package com.example.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Factura
import kotlinx.coroutines.launch

class FiltroViewModel: ViewModel() {

//    var facturaState by mutableStateOf(FacturaState())
//        private set

    var fechaDesde by mutableStateOf<Long?>(null)
        private set

    var fechaHasta by mutableStateOf<Long?>(null)
        private set


    var sliderPosition by mutableStateOf(0f)
        private set

    var checkedState by mutableStateOf(List(5){false})
        private set

    val filtro = FacturaFiltroState(
        fechaDesde = "01/01/1990",
        fechaHasta = "23/04/2025",
        importeMin = 0.0,
        importeMax = 300.0,
        estado = listOf("pagada", "pendiente"),
        succes = true
    )


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

//    fun FiltrarFacturas(facturafiltrada: FacturaFiltroState) {
//
//
//    }
}