package com.mycaruae.app.feature.mileage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mycaruae.app.ui.components.CocTopBar
import com.mycaruae.app.ui.components.LoadingScreen
import com.mycaruae.app.ui.theme.CocIcons

@Composable
fun MileageEntryScreen(
    onNavigateBack: () -> Unit,
    viewModel: MileageEntryViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onNavigateBack()
    }

    Scaffold(
        topBar = {
            CocTopBar(
                title = "Add Mileage",
                showBack = true,
                onBackClick = onNavigateBack,
            )
        },
    ) { padding ->
        if (state.isLoading && state.newMileage.isEmpty()) {
            LoadingScreen(modifier = Modifier.padding(padding))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
            ) {
                // Current mileage card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            imageVector = CocIcons.Mileage,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp),
                            tint = MaterialTheme.colorScheme.primary,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Current Odometer",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "%,d km".format(state.currentMileage),
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // New mileage input
                Text(
                    text = "Enter new odometer reading",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = state.newMileage,
                    onValueChange = viewModel::onMileageChange,
                    label = { Text("New Mileage (km)") },
                    placeholder = { Text("e.g. ${state.currentMileage + 500}") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = state.error != null,
                    supportingText = state.error?.let { { Text(it) } },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        viewModel.saveMileage()
                    }),
                    suffix = { Text("km") },
                )

                // Show difference if valid
                val newKm = state.newMileage.toIntOrNull()
                if (newKm != null && newKm > state.currentMileage) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "+ %,d km since last entry".format(newKm - state.currentMileage),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Save button
                Button(
                    onClick = viewModel::saveMileage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = !state.isLoading,
                ) {
                    Text(
                        text = if (state.isLoading) "Saving..." else "Save Mileage",
                        style = MaterialTheme.typography.labelLarge,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}