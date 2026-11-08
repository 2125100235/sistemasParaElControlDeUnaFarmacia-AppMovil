package com.logimedtech.farmacia.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logimedtech.farmacia.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onCorreoChanged(nuevoCorreo: String) {
        _uiState.update { currentState ->
            currentState.copy(correo = nuevoCorreo, error = null)
        }
    }

    fun onClaveChanged(nuevaClave: String) {
        _uiState.update { currentState ->
            currentState.copy(clave = nuevaClave, error = null)
        }
    }

    fun IniciarSesion(){
        // Abre la ejecución en segundo plano o corrutina
        viewModelScope.launch {
            // Cambia el estado a cargando
            _uiState.update { it.copy(cargando = true) }
            try {
                val response = RetrofitClient.api.login(
                    correo = _uiState.value.correo,
                    clave = _uiState.value.clave
                )
                if (response.exito) {
                    _uiState.update {
                        it.copy(
                            usuario = response.usuario,
                            error = null,
                            cargando = false
                        )
                    }
                }
                else {
                    _uiState.update {
                        it.copy(
                            error = response.mensaje,
                            cargando = false
                        )
                    }
                }
        }
            catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.localizedMessage,
                        cargando = false
                    )
                }
            }
        }
    }

}