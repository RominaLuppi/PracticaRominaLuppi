package com.example.core.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GetFacturasUseCase
import com.example.domain.Factura
import kotlinx.coroutines.launch

class FacturaViewModel: ViewModel() {

//    var state by mutableStateOf<FacturaState>(FacturaState(mutableListOf()))
//        private set
    val factura = MutableLiveData<List<Factura>>()
    val isLoading = MutableLiveData<Boolean>()
    var getFacturasUseCase = GetFacturasUseCase()


    init {
    viewModelScope.launch {
        isLoading.postValue(true)
        val result: List<Factura>? = getFacturasUseCase()

        if(!result.isNullOrEmpty()){
            factura.postValue(result)
            isLoading.postValue(false)
        }

    }
    }
}