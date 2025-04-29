package com.example.core.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.ui.view.FacturaFiltroState
import com.example.domain.GetFacturasUseCase
import com.example.domain.Factura
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FacturaViewModel @Inject constructor(
    private val getFacturasUseCase: GetFacturasUseCase
) : ViewModel() {

    private val _factura = MutableLiveData<List<Factura>>()
    val factura: LiveData<List<Factura>> = _factura

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    private var facturaOriginal: List<Factura> = emptyList()

    init {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val result: List<Factura>? = getFacturasUseCase()
                facturaOriginal = result ?: emptyList()
                _factura.postValue(result ?: emptyList())
            } catch (e: Exception) {
                _errorMsg.postValue("Error al recuperar las facturas")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun filtrarFacturas(filtro: FacturaFiltroState) {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val fechaDesde = filtro.fechaDesde.takeIf { it.isNotBlank() }?.let {
            formatoFecha.parse(it)?.time
        }
        val fechaHasta = filtro.fechaHasta.takeIf { it.isNotBlank() }?.let {
            formatoFecha.parse(it)?.time
        }
        val listaFiltrada = facturaOriginal.filter { factura ->
            val fechaFactura = try {
                formatoFecha.parse(factura.fecha)?.time
            } catch (e: Exception) {
                null
            }
            val fechaValida = when {
                fechaDesde != null && fechaHasta != null -> {
                    fechaFactura != null && fechaFactura in fechaDesde..fechaHasta
                }

                fechaDesde != null -> {
                    fechaFactura != null && fechaFactura >= fechaDesde
                }

                fechaHasta != null -> {
                    fechaFactura != null && fechaFactura <= fechaHasta
                }

                else -> true
            }

            val importeValido = factura.importeOrdenacion in filtro.importeMin..filtro.importeMax
            val estadoValido =
                filtro.estado.isEmpty() || filtro.estado.contains(factura.descEstado.lowercase())


            fechaValida && importeValido && estadoValido
        }

        _factura.postValue(listaFiltrada)
    }

}