package com.logimedtech.farmacia.model

data class Proveedor(
    val idProveedor: Int,
    val nombre: String,
    val telefono: String,
    val correo: String,
    val direccion: String,
    val estado: Int
)
