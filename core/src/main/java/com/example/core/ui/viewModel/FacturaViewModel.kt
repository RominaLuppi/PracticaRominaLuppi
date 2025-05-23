package com.example.core.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.FacturasDao
import com.example.data.repository.GetFacturasUseCaseData
import com.example.domain.Factura
import com.example.domain.FacturaFiltroState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class FacturaViewModel @Inject constructor(
    private val getFacturasUseCaseData: GetFacturasUseCaseData,
    private val facturasDao: FacturasDao
) : ViewModel() {

    private val _factura = MutableLiveData<List<Factura>>()
    val factura: LiveData<List<Factura>> = _factura

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    private var facturaOriginal: List<Factura> = emptyList()

    private val _maxImporte = MutableLiveData<Double>()
    val maxImporte: LiveData<Double> = _maxImporte

    init {
        cargarFacturas()
    }

    fun cargarFacturas() {
        viewModelScope.launch {

            _isLoading.postValue(true)
            try {
                val result: List<Factura>? = getFacturasUseCaseData()
                Log.d("APP", "Facturas recibidas: ${result}")
                facturaOriginal = result ?: emptyList()
                _factura.postValue(result ?: emptyList())
                _maxImporte.postValue(result?.maxOfOrNull { it.importeOrdenacion }) //se saca el maximo importe
            } catch (e: Exception) {
                _errorMsg.postValue("Error al recuperar las facturas")
                Log.e("APP", "Error al obtener facturas", e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun formatearFecha(fecha: String): String {

        val formatoFechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formatoFechaNueva = SimpleDateFormat("dd MMM yyyy", Locale("es", "ES"))
        val fechaNueva: Date = formatoFechaActual.parse(fecha)

        val fechaFormateada = formatoFechaNueva.format(fechaNueva)
        val partes = fechaFormateada.split(" ")
        if (partes.size == 3) {
            val dia = partes[0]
            val mes = partes[1].replaceFirstChar { it.uppercase() }
            val año = partes[2]
            return ("$dia $mes $año")
        }

        return fechaFormateada
    }

    fun filtrarFacturas(facturasOrig: List<Factura>, filtro: FacturaFiltroState): List<Factura> {

        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return facturasOrig.filter { factura ->
            var esValido = true

            // Verificar si la factura tiene una fecha válida
            val fechaFactura = if (factura.fecha.isNotEmpty()) {
                formatoFecha.parse(factura.fecha)
            } else {
                null  // Si la fecha de la factura está vacía, no la consideramos
            }

            //filtro por fecha desde
            if (filtro.fechaDesde.isNotEmpty() && fechaFactura != null) {
                val fechaDesde = formatoFecha.parse(filtro.fechaDesde)

                if (fechaFactura.before(fechaDesde) == true) {
                    esValido = false
                }
            }

            //filtro por fecha hasta
            if (filtro.fechaHasta.isNotEmpty() && fechaFactura != null) {
                val fechaHasta = formatoFecha.parse(filtro.fechaHasta)

                if (fechaFactura.after(fechaHasta) == true) {
                    esValido = false
                }
            }
            //filtro por importe minimo
            if (filtro.importeMin > 0 && factura.importeOrdenacion < filtro.importeMin) {
                esValido = false
            }
            //filtro por importe maximo
            if (filtro.importeMax > 0 && factura.importeOrdenacion > filtro.importeMax) {
                esValido = false
            }
            //filtro por estado
            if (filtro.estado.isNotEmpty() && !filtro.estado.contains(factura.descEstado)) {
                esValido = false
            }
            esValido
        }
    }

    //para actualizar la lista de facturas en el FacturasViewModel
    fun actualizarFacturas(facturas: List<Factura>) {
        _factura.value = facturas
    }

    //para restablecer las facturas a su estado original sin filtros
    fun resetFacturas() {
        _factura.value = facturaOriginal
    }

     suspend fun clearAllDatabase(){
         facturasDao.deleteAllDatabase()
     }
}