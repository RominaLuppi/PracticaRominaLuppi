package com.example.core.ui.viewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.FacturaFiltroState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FiltroViewModel: ViewModel() {

    var fechaDesde by mutableStateOf<Long?>(null)
//        private set
    var fechaHasta by mutableStateOf<Long?>(null)
//        private set
    var sliderPosition by mutableStateOf(0f)
//        private set
    var checkedState by mutableStateOf(List(5) { false })
//        private set

    val _errorMsgDesde = MutableStateFlow<String?>(null)
    val errorMsgDesde = _errorMsgDesde.asStateFlow()

    val _errorMsgHasta = MutableStateFlow<String?>(null)
    val errorMsgHasta = _errorMsgHasta.asStateFlow()

    val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg = _errorMsg.asStateFlow()

    fun motrarMsgErrorFechaDesde(mensaje: String){
        _errorMsgDesde.value = mensaje
    }

    fun limpiarMsgErrorFechaDesde(){
        _errorMsgDesde.value = null
    }

    fun motrarMsgErrorFechaHasta(mensaje: String){
        _errorMsgHasta.value = mensaje
    }

    fun limpiarMsgErrorFechaHasta(){
        _errorMsgHasta.value = null
    }

    fun mostrarMsgError(mensaje: String){
        _errorMsg.value =mensaje
    }

    fun limpiarMsgError(){
        _errorMsg.value = null
    }

    fun ActualizarFechaDesde(date: Long?){
        if(date == null)
            return
        val hoy = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
      if(date > hoy){
            val msg = "Debe seleccionar una fecha válida"
            motrarMsgErrorFechaDesde(msg)
      }else{
          fechaDesde = date
      }
    }

    fun ActualizarFechaHasta(date: Long?){
        if(date == null)
            return
        val hoy = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        if (date > hoy){
            val msg = "Debe seleccionar una fecha válida"
            motrarMsgErrorFechaHasta(msg)
        }else{
            fechaHasta = date
        }
    }

    fun SelectorImporte(value: Float){
        sliderPosition = value
    }

    fun SelectorEstado(index: Int, value: Boolean){
        checkedState = checkedState.toMutableList().apply { this[index] = value }

    }

    fun ResetarFiltros(){
        fechaDesde = null
        fechaHasta = null
        sliderPosition = 0f
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