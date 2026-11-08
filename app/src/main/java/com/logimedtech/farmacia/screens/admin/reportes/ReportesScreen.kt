package com.logimedtech.farmacia.screens.admin.reportes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ReportesRoute(viewModel: ReportesViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ReportesScreen(
        uiState = uiState,
    )
}

@Composable
fun ReportesScreen(
    uiState: ReportesUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Reportes")
    }
}
