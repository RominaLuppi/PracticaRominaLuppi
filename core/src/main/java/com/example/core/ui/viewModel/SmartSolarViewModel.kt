package com.example.core.ui.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.data.MockDetalleSmartSolar
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SmartSolarViewModel @Inject constructor() : ViewModel()  {
    var detalle by mutableStateOf(MockDetalleSmartSolar())

}