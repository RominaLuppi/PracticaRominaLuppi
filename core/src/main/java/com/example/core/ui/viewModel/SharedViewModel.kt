package com.example.core.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.Factura

class SharedViewModel : ViewModel() {
    private val _facturasFiltradas = MutableLiveData<List<Factura>>()
    val facturasFiltradas: LiveData<List<Factura>> = _facturasFiltradas

    fun setFacturasFiltradas(facturas: List<Factura>){
        _facturasFiltradas.value = facturas
    }
}