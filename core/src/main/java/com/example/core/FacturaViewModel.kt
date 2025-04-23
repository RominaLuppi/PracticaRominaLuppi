package com.example.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Factura
import kotlinx.coroutines.launch

class FacturaViewModel: ViewModel() {

    var state by mutableStateOf<FacturaState>(FacturaState(mutableListOf()))
        private set



    init {
    viewModelScope.launch {
        state.facturaList = listOf(
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






}