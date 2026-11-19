package com.logimedtech.farmacia.model

import com.google.gson.annotations.SerializedName

data class Empleado(
    @SerializedName("id_empleado") val id_empleado: Int = 0,
    val nombre: String = "",
    @SerializedName("apellido_paterno") val apellido_paterno: String = "",
    @SerializedName("apellido_materno") val apellido_materno: String = "",
    val clave: String = "",
    val correo: String = "",
    val telefono: String = "",
    val rol: String = "Empleado",
    val estado: Int = 1
)

data class EmpleadosResponse(
    val exito: Boolean = false,
    val mensaje: String = "",
    val datos: List<Empleado> = emptyList()
)