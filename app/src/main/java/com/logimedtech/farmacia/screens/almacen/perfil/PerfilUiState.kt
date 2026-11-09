package com.logimedtech.farmacia.screens.almacen.perfil

import com.logimedtech.farmacia.model.Empleado

data class PerfilUiState(
    val usuario: Empleado? = null,
    val cargando: Boolean = false,
    val error : String? = null
)
