package com.logimedtech.farmacia.model

data class Empleado(
    val id_empleado: Int? = null,
    val nombre: String? = null,
    val apellido_paterno: String? = null,
    val apellido_materno: String? = null,
    val clave: String? = null,
    val correo: String? = null,
    val telefono: String? = null,
    val rol: String? = null,
    val estado: Int? = null,
)
