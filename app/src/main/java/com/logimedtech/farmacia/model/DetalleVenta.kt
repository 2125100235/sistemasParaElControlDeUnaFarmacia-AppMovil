package com.logimedtech.farmacia.model

data class DetalleVenta(
    val numero_nota: Int,
    val codigo_producto: Int,
    val cantidad_salida: Int,
    val precio_venta: Float,
    val subTotal: Float
)
