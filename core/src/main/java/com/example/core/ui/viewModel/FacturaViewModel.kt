package com.example.core.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GetFacturasUseCase
import com.example.domain.Factura
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FacturaViewModel @Inject constructor(
    private val getFacturasUseCase : GetFacturasUseCase
): ViewModel() {

    private val _factura = MutableLiveData<List<Factura>>()
    val factura: LiveData<List<Factura>> = _factura
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    init {
    viewModelScope.launch {
        _isLoading.postValue(true)
        val result: List<Factura>? = getFacturasUseCase()

        _factura.postValue(result ?: emptyList())
        _isLoading.postValue(false)

    }
    }
}