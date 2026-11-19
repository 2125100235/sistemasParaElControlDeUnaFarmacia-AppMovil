package com.logimedtech.farmacia.screens.admin.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logimedtech.farmacia.model.Empleado
import com.logimedtech.farmacia.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonalViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PersonalUiState())
    val uiState: StateFlow<PersonalUiState> = _uiState.asStateFlow()

    init {
        obtenerEmpleados()
    }

    fun obtenerEmpleados(query: String? = null) {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true, error = null) }
            try {
                val respuesta = RetrofitClient.api.obtenerEmpleados(buscar = query)
                if (respuesta.exito) {
                    _uiState.update { it.copy(listaEmpleados = respuesta.datos, cargando = false) }
                } else {
                    _uiState.update { it.copy(error = respuesta.mensaje, cargando = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error de conexión: ${e.message}", cargando = false) }
            }
        }
    }

    fun enCambioTextoBusqueda(nuevoTexto: String) {
        _uiState.update { it.copy(textoBusqueda = nuevoTexto) }
        obtenerEmpleados(query = nuevoTexto.ifBlank { null })
    }

    fun abrirFormularioCrear() {
        _uiState.update { it.copy(mostrarDialogoFormulario = true, empleadoAEditar = null) }
    }

    fun abrirFormularioEditar(empleado: Empleado) {
        _uiState.update { it.copy(mostrarDialogoFormulario = true, empleadoAEditar = empleado) }
    }

    fun cerrarFormulario() {
        _uiState.update { it.copy(mostrarDialogoFormulario = false, empleadoAEditar = null) }
    }

    fun confirmarEliminar(empleado: Empleado) {
        _uiState.update { it.copy(empleadoAEliminar = empleado) }
    }

    fun cancelarEliminar() {
        _uiState.update { it.copy(empleadoAEliminar = null) }
    }

    fun guardarEmpleado(
        nombre: String, apePaterno: String, apeMaterno: String,
        correo: String, telefono: String, rol: String, clave: String
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true) }
            try {
                val actual = _uiState.value.empleadoAEditar
                val respuesta = if (actual == null) {
                    RetrofitClient.api.crearEmpleado(
                        nombre, apePaterno, apeMaterno, clave, correo, telefono, rol
                    )
                } else {
                    RetrofitClient.api.actualizarEmpleado(
                        actual.id_empleado, nombre, apePaterno, apeMaterno,
                        clave.ifBlank { null }, correo, telefono, rol
                    )
                }

                if (respuesta.exito) {
                    cerrarFormulario()
                    obtenerEmpleados(_uiState.value.textoBusqueda.ifBlank { null })
                } else {
                    _uiState.update { it.copy(error = respuesta.mensaje, cargando = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error: ${e.message}", cargando = false) }
            }
        }
    }

    fun ejecutarEliminacion() {
        val empleado = _uiState.value.empleadoAEliminar ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true) }
            try {
                val respuesta = RetrofitClient.api.eliminarEmpleado(empleado.id_empleado)
                if (respuesta.exito) {
                    cancelarEliminar()
                    obtenerEmpleados(_uiState.value.textoBusqueda.ifBlank { null })
                } else {
                    _uiState.update { it.copy(error = respuesta.mensaje, cargando = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error: ${e.message}", cargando = false) }
            }
        }
    }
}