package com.logimedtech.farmacia.model

data class DetalleVenta(
    val numeroNota: Int,
    val codigoProducto: Int,
    val cantidadSalida: Int,
    val precioVenta: Float,
    val subTotal: Float
)
