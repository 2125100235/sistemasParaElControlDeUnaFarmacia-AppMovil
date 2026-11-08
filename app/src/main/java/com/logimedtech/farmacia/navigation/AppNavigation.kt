package com.logimedtech.farmacia.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.logimedtech.farmacia.screens.admin.reportes.ReportesRoute
import com.logimedtech.farmacia.screens.almacen.inicio.Inicio
import com.logimedtech.farmacia.screens.login.LoginRoute

@Composable
fun AppNavigation() {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    val loginBackStack = rememberNavBackStack(AppDestination.Login)
    val adminBackStack = rememberNavBackStack(AppDestination.AdminDashboard)
    val almacenBackStack = rememberNavBackStack(AppDestination.InventarioAlmacen)

    val appEntryProvider = entryProvider<NavKey> {
        entry<AppDestination.Login> {
            LoginRoute(
                onLoginSuccess = { usuario ->
                    when (usuario.rol) {
                        "Administrador" -> {
                            loginBackStack.clear()
                            selectedIndex = 1
                        }
                        else -> {
                            loginBackStack.clear()
                            selectedIndex = 2
                        }
                    }
                }
            )
        }

        entry<AppDestination.AdminDashboard> {
            ReportesRoute()
        }

        entry<AppDestination.InventarioAlmacen> {
            Inicio()
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

    val currentBackStack = when (selectedIndex) {
        0 -> loginBackStack
        1 -> adminBackStack
        else -> almacenBackStack
    }

    val currentEntries = when (selectedIndex) {
        0 -> loginEntries
        1 -> adminEntries
        else -> almacenEntries
    }

    BackHandler(enabled = selectedIndex != 0 && currentBackStack.size == 1) {
        selectedIndex = 0
    }

    Scaffold(
        bottomBar = {
            if (selectedIndex != 0) {
                NavigationBar {
                    NavigationBarItem(
                        selected = selectedIndex == 1,
                        onClick = { selectedIndex = 1 },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Admin") },
                        label = { Text("Admin") }
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 2,
                        onClick = { selectedIndex = 2 },
                        icon = { Icon(Icons.Default.List, contentDescription = "Inventario") },
                        label = { Text("Inventario") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavDisplay(
            entries = currentEntries,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding),
            onBack = { currentBackStack.removeLastOrNull() }
        )
    }
}
