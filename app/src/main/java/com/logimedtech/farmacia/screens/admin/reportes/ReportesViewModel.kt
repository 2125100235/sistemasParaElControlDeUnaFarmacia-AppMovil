package com.logimedtech.farmacia.screens.admin.reportes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logimedtech.farmacia.model.Totales
import com.logimedtech.farmacia.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel para los Reportes de Cierre
class ReportesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ReportesUiState())

    val uiState: StateFlow<ReportesUiState> = _uiState.asStateFlow()
    private val _totales = MutableStateFlow(Totales(0.0f, 0.0f, 0.0f))
    val totales: StateFlow<Totales> = _totales

    init {
        cargarTotales()
    }

    fun cargarTotales() {
        viewModelScope.launch {
            try {
                _totales.value = RetrofitClient.api.getTotales()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}