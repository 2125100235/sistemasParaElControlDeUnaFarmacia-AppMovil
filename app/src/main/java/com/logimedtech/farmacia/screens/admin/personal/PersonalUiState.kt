package com.logimedtech.farmacia.screens.admin.personal

import com.logimedtech.farmacia.model.Empleado

data class PersonalUiState(
    val listaEmpleados: List<Empleado> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null,
    val textoBusqueda: String = "",
    val mostrarDialogoFormulario: Boolean = false,
    val empleadoAEditar: Empleado? = null,
    val empleadoAEliminar: Empleado? = null
)