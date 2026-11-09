package com.logimedtech.farmacia.screens.perfil

import androidx.lifecycle.ViewModel
import com.logimedtech.farmacia.model.UserSession
import com.logimedtech.farmacia.screens.almacen.perfil.PerfilUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PerfilViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        cargarPerfil()
    }

    fun cargarPerfil() {
        val empleadoActual = UserSession.currentUser.value
        if (empleadoActual != null) {
            _uiState.update {
                it.copy(usuario = empleadoActual, cargando = false, error = null)
            }
        } else {
            _uiState.update {
                it.copy(error = "No hay información de sesión disponible", cargando = false)
            }
        }
    }

    fun cerrarSesion(onLogout: () -> Unit) {
        UserSession.cerrarSesion()
        onLogout()
    }
}