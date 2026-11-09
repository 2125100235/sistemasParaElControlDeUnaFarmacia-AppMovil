package com.logimedtech.farmacia.model

import com.logimedtech.farmacia.model.Empleado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object UserSession {
    private val _currentUser = MutableStateFlow<Empleado?>(null)
    val currentUser: StateFlow<Empleado?> = _currentUser.asStateFlow()

    fun guardarSesion(usuario: Empleado) {
        _currentUser.value = usuario
    }

    fun cerrarSesion() {
        _currentUser.value = null
    }
}
