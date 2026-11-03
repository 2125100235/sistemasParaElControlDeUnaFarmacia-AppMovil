package com.logimedtech.farmacia.model

data class Empleado(
    val idEmpleado: Int,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val clave: String,
    val correo: String,
    val telefono: String,
    val rol: String,
    val estado: Int,
)
