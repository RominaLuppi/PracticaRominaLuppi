package com.example.core.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Factura
import com.example.domain.FacturaFiltroState
import com.example.domain.GetFacturasFiltradasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getFacturasFiltradasUseCase : GetFacturasFiltradasUseCase
): ViewModel()
{
    val _facturasFiltradas = MutableLiveData<List<Factura>>()
    var facturasFiltradas: LiveData<List<Factura>> = _facturasFiltradas

    val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    private val _filtroState = MutableLiveData(FacturaFiltroState("", "", 0.0, 0.0, emptyList(), 0.0))
    val filtroState: LiveData<FacturaFiltroState> = _filtroState

    //cuando se cambian los fltros, se actualiza e estado y se realiza el filtrado
    fun updateFiltroState(filtro: FacturaFiltroState){
        _filtroState.value = filtro
        fetchFacturasFiltradas(filtro)
    }

    private fun fetchFacturasFiltradas(filtro: FacturaFiltroState) {

        viewModelScope.launch {
            try {
                val result = getFacturasFiltradasUseCase(filtro)
                _facturasFiltradas.postValue(result)
            }catch (e: Exception){
                _errorMsg.postValue("Error al filtrar las facturas")
            }
        }
    }
}

