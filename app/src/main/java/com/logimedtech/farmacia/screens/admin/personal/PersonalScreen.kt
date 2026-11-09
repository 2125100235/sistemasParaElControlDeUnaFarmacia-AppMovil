package com.logimedtech.farmacia.screens.admin.personal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PersonalRoute(
    viewModel: PersonalViewModel = viewModel()
) {
    PersonalScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Personal") }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Módulo de Personal")
        }
    }
}
