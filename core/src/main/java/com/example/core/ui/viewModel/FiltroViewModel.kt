package com.example.core.ui.viewModel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.R
import com.example.core.ui.view.FacturaFiltroState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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
    private val _filtro = MutableLiveData<FacturaFiltroState>()
    val filtro : LiveData<FacturaFiltroState> = _filtro

    fun actualizarFiltro(filtro: FacturaFiltroState){
        _filtro.value = filtro
    }

    //se construye el filtro con los parametros que necesitamos para filtrar
    fun ConstruirFiltroState(): FacturaFiltroState {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaDesdeState = fechaDesde?.let { formatoFecha.format(it) } ?: ""
        val fechaHastaState = fechaHasta?.let { formatoFecha.format(it) } ?: ""

        val estadosDisponibles = listOf("Pagada", "Anulada", "Cuota fija", "Pendiente de pago", "Plan de pago")
        val estadosSeleccionados = estadosDisponibles.filter { checkedState[estadosDisponibles.indexOf(it)] }

        return FacturaFiltroState(
            fechaDesde = fechaDesdeState,
            fechaHasta = fechaHastaState,
            importeMin = 0.0,
            importeMax = sliderPosition.toDouble(),
            estado = estadosSeleccionados
        )


    }


}