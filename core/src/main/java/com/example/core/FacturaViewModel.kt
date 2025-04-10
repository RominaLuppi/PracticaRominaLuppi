package com.example.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.Factura

class FacturaViewModel: ViewModel() {

    private val _facturas = MutableLiveData<List<Factura>>(emptyList())
    val facturas: LiveData<List<Factura>> get() = _facturas
}