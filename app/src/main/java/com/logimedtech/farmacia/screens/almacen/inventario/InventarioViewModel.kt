package com.logimedtech.farmacia.screens.almacen.inventario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logimedtech.farmacia.model.Producto
import com.logimedtech.farmacia.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InventarioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InventarioUiState())
    val uiState: StateFlow<InventarioUiState> = _uiState.asStateFlow()

    init {
        obtenerProductos()
    }

    fun obtenerProductos(query: String? = null) {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true, error = null) }
            try {
                val respuesta = RetrofitClient.api.obtenerProductos(buscar = query)
                if (respuesta.exito) {
                    _uiState.update {
                        it.copy(listaProductos = respuesta.datos, cargando = false)
                    }
                } else {
                    _uiState.update {
                        it.copy(error = respuesta.mensaje, cargando = false)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Error de conexión: ${e.message}", cargando = false)
                }
            }
        }
    }

    fun enCambioTextoBusqueda(nuevoTexto: String) {
        _uiState.update { it.copy(textoBusqueda = nuevoTexto) }
        obtenerProductos(query = nuevoTexto.ifBlank { null })
    }

    fun abrirFormularioCrear() {
        _uiState.update { it.copy(mostrarDialogoFormulario = true, productoAEditar = null) }
    }

    fun abrirFormularioEditar(producto: Producto) {
        _uiState.update { it.copy(mostrarDialogoFormulario = true, productoAEditar = producto) }
    }

    fun cerrarFormulario() {
        _uiState.update { it.copy(mostrarDialogoFormulario = false, productoAEditar = null) }
    }

    fun confirmarEliminar(producto: Producto) {
        _uiState.update { it.copy(productoAEliminar = producto) }
    }

    fun cancelarEliminar() {
        _uiState.update { it.copy(productoAEliminar = null) }
    }

    fun guardarProducto(
        nombre: String,
        tipo: String,
        laboratorio: String,
        lote: String,
        caducidad: String,
        existencia: Int,
        precio: Double
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true) }
            try {
                val productoActual = _uiState.value.productoAEditar
                val respuesta = if (productoActual == null) {
                    RetrofitClient.api.crearProducto(
                        nombre = nombre,
                        tipoMedicamento = tipo,
                        laboratorio = laboratorio,
                        lote = lote,
                        fechaCaducidad = caducidad,
                        existencia = existencia,
                        precioVenta = precio
                    )
                } else {
                    RetrofitClient.api.actualizarProducto(
                        codigo = productoActual.codigo,
                        nombre = nombre,
                        tipoMedicamento = tipo,
                        laboratorio = laboratorio,
                        lote = lote,
                        fechaCaducidad = caducidad,
                        existencia = existencia,
                        precioVenta = precio
                    )
                }

                if (respuesta.exito) {
                    cerrarFormulario()
                    obtenerProductos(_uiState.value.textoBusqueda.ifBlank { null })
                } else {
                    _uiState.update { it.copy(error = respuesta.mensaje, cargando = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al guardar: ${e.message}", cargando = false) }
            }
        }
    }

    fun ejecutarEliminacion() {
        val producto = _uiState.value.productoAEliminar ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true) }
            try {
                val respuesta = RetrofitClient.api.eliminarProducto(codigo = producto.codigo)
                if (respuesta.exito) {
                    cancelarEliminar()
                    obtenerProductos(_uiState.value.textoBusqueda.ifBlank { null })
                } else {
                    _uiState.update { it.copy(error = respuesta.mensaje, cargando = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al eliminar: ${e.message}", cargando = false) }
            }
        }
    }
}