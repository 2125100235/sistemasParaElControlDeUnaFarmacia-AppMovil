package com.logimedtech.farmacia.model

data class LoginResponse(
    val exito: Boolean,
    val mensaje: String,
    // El ? significa que puede ser nulo si el login falla
    val usuario: Empleado?
)
