package com.example.core.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GetFacturasUseCase
import com.example.domain.Factura
import kotlinx.coroutines.launch

class FacturaViewModel: ViewModel() {

    private val _factura = MutableLiveData<List<Factura>>()
    val factura: LiveData<List<Factura>> = _factura
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val getFacturasUseCase = GetFacturasUseCase()


    init {
    viewModelScope.launch {
        _isLoading.postValue(true)
        val result: List<Factura>? = getFacturasUseCase()

        _factura.postValue(result ?: emptyList())
        _isLoading.postValue(false)

    }
    }
}