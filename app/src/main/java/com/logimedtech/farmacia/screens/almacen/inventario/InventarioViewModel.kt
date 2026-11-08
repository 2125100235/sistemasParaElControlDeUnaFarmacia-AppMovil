package com.logimedtech.farmacia.screens.almacen.inventario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logimedtech.farmacia.model.Producto
import com.logimedtech.farmacia.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InventarioViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(InventarioUiState())
    val uiState: StateFlow<InventarioUiState> = _uiState.asStateFlow()

    // 1. Estado privado (solo el ViewModel puede modificar los datos)
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())

    // 2. Estado público (la pantalla en Compose solo puede LEER los datos)
    val productos: StateFlow<List<Producto>> = _productos

    fun obtenerProductos() {
        // Corrutina para no congelar la app
        viewModelScope.launch {
            try {
                // Hacemos la llamada a Retrofit y guardamos la respuesta en el StateFlow
                val lista = RetrofitClient.api.getProductos()
                _productos.value = lista
            } catch (e: Exception) {
                // Manejar error de conexión
            }
        }
    }
}

