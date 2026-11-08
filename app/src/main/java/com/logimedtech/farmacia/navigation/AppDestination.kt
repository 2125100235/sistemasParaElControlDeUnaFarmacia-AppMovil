package com.logimedtech.farmacia.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface AppDestination : NavKey {
    @Serializable
    data object Login : AppDestination

    @Serializable
    data object AdminDashboard : AppDestination

    @Serializable
    data object InventarioAlmacen : AppDestination

    @Serializable
    data object Reportes : AppDestination

    @Serializable
    data object Perfil : AppDestination
}
