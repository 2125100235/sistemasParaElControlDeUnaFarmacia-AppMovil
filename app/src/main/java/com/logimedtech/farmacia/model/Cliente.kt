package com.logimedtech.farmacia.model

data class Cliente(
    val codigo: Int,
    val nombre: String,
    val telefono: String,
    val rfc: String,
    val direccion: String,
    val estado: Int
)
