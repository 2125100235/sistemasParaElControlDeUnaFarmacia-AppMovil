package com.logimedtech.farmacia.screens.login

import com.logimedtech.farmacia.model.Empleado

data class LoginUiState(
    val correo: String = "",
    val clave: String = "",
    val cargando: Boolean = false,
    val error: String? = null,
    val usuario: Empleado? = null
)