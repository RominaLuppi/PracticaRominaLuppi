package com.example.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Factura
import kotlinx.coroutines.launch
import java.util.EnumSet.of

class FacturaViewModel: ViewModel() {

//    private val _facturas = MutableLiveData<List<Factura>>(emptyList())
//    val facturas: LiveData<List<Factura>> get() = _facturas

    var facturas by mutableStateOf(listOf<Factura>())
        private set

    init {
        viewModelScope.launch {
            facturas = listOf(
                Factura("pendiente", 30.30, "21/09/2020"),
                Factura("pagada", 100.89, "30/08/2025"),
                Factura("pagada", 350.89, "30/08/2025"),
                Factura("pagada", 50.89, "30/08/2025"),
                Factura("pagada", 100.89, "30/08/2025"),
                Factura("pagada", 350.89, "09/08/2025"),
                Factura("pendiente", 50.89, "30/08/2025"),
            )
        }
    }

    var fechaDesde by mutableStateOf<Long?>(null)
        private set
    var fechaHasta by mutableStateOf<Long?>(null)
        private set

    var sliderPosition by mutableStateOf(0f)
        private set

    var checkedState by mutableStateOf(List(5){false})
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

    fun FiltrarFacturas() {

    }



}