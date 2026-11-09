package com.logimedtech.farmacia.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.logimedtech.farmacia.model.UserSession
import com.logimedtech.farmacia.screens.admin.perfil.PerfilRoute
import com.logimedtech.farmacia.screens.admin.personal.PersonalRoute
import com.logimedtech.farmacia.screens.admin.reportes.ReportesRoute
import com.logimedtech.farmacia.screens.almacen.inventario.InventarioRoute
import com.logimedtech.farmacia.screens.login.LoginRoute

@Composable
fun AppNavigation() {
    var userRole by rememberSaveable { mutableStateOf<String?>(null) }

    val loginBackStack = rememberNavBackStack(AppDestination.Login)
    val adminBackStack = rememberNavBackStack(AppDestination.AdminReportes)
    val almacenBackStack = rememberNavBackStack(AppDestination.InventarioAlmacen)

    val appEntryProvider = entryProvider<NavKey> {
        // --- LOGIN ---
        entry<AppDestination.Login> {
            LoginRoute(
                onLoginSuccess = { usuario ->
                    UserSession.guardarSesion(usuario)
                    userRole = usuario.rol
                }
            )
        }

        // --- RUTAS ADMINISTRADOR ---
        entry<AppDestination.AdminReportes> {
            ReportesRoute()
        }

        entry<AppDestination.AdminPersonal> {
            PersonalRoute()
        }

        // --- RUTAS ALMACENISTA ---
        entry<AppDestination.InventarioAlmacen> {
            InventarioRoute()
        }

        // --- RUTA COMPARTIDA PERFIL ---
        entry<AppDestination.Perfil> {
            PerfilRoute(
                onLogout = { userRole = null }
            )
        }
    }

    val loginEntries = rememberDecoratedNavEntries(
        backStack = loginBackStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = appEntryProvider
    )

    val adminEntries = rememberDecoratedNavEntries(
        backStack = adminBackStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = appEntryProvider
    )

    val almacenEntries = rememberDecoratedNavEntries(
        backStack = almacenBackStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = appEntryProvider
    )

    BackHandler(enabled = userRole != null) {
        userRole = null
    }

    val currentBackStack = when (userRole) {
        "Administrador" -> adminBackStack
        "Almacenista" -> almacenBackStack
        else -> loginBackStack
    }

    Scaffold(
        bottomBar = {
            when (userRole) {
                "Administrador" -> {
                    val currentDestination = adminBackStack.lastOrNull()
                    NavigationBar {
                        NavigationBarItem(
                            selected = currentDestination == AppDestination.AdminReportes,
                            onClick = {
                                adminBackStack.clear()
                                adminBackStack.add(AppDestination.AdminReportes)
                            },
                            icon = { Icon(Icons.Default.BarChart, contentDescription = "Reportes") },
                            label = { Text("Reportes") }
                        )
                        NavigationBarItem(
                            selected = currentDestination == AppDestination.AdminPersonal,
                            onClick = {
                                adminBackStack.clear()
                                adminBackStack.add(AppDestination.AdminPersonal)
                            },
                            icon = { Icon(Icons.Default.Group, contentDescription = "Personal") },
                            label = { Text("Personal") }
                        )
                        NavigationBarItem(
                            selected = currentDestination == AppDestination.Perfil,
                            onClick = {
                                adminBackStack.clear()
                                adminBackStack.add(AppDestination.Perfil)
                            },
                            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                            label = { Text("Perfil") }
                        )
                    }
                }

                "Almacenista" -> {
                    val currentDestination = almacenBackStack.lastOrNull()
                    NavigationBar {
                        NavigationBarItem(
                            selected = currentDestination == AppDestination.InventarioAlmacen,
                            onClick = {
                                almacenBackStack.clear()
                                almacenBackStack.add(AppDestination.InventarioAlmacen)
                            },
                            icon = { Icon(Icons.Default.Inventory, contentDescription = "Inventario") },
                            label = { Text("Inventario") }
                        )
                        NavigationBarItem(
                            selected = currentDestination == AppDestination.Perfil,
                            onClick = {
                                almacenBackStack.clear()
                                almacenBackStack.add(AppDestination.Perfil)
                            },
                            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                            label = { Text("Perfil") }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        val currentEntries = when (userRole) {
            "Administrador" -> adminEntries
            "Almacenista" -> almacenEntries
            else -> loginEntries
        }

        NavDisplay(
            entries = currentEntries,
            onBack = { currentBackStack.removeLastOrNull() },
            modifier = Modifier.padding(innerPadding)
        )
    }
}