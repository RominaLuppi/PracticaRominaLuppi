package com.example.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.Factura
import java.util.EnumSet.of

class FacturaViewModel: ViewModel() {

    private val _facturas = MutableLiveData<List<Factura>>(emptyList())
    val facturas: LiveData<List<Factura>> get() = _facturas


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

}