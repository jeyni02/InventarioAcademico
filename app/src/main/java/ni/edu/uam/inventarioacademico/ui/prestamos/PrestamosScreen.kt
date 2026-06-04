package ni.edu.uam.inventarioacademico.ui.prestamos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ni.edu.uam.inventarioacademico.data.local.entity.Equipo
import ni.edu.uam.inventarioacademico.viewmodel.PrestamoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrestamosScreen(
    navController: NavController,
    viewModel: PrestamoViewModel
) {
    var solicitante by remember { mutableStateOf("") }
    var equipoSeleccionado by remember { mutableStateOf<Equipo?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val prestamos by viewModel.prestamos.collectAsState()
    val equiposDisponibles by viewModel.equiposDisponibles.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "🤝 Gestión de Préstamos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "➕ Nuevo Préstamo", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = solicitante,
                    onValueChange = { solicitante = it },
                    label = { Text("Nombre del Solicitante") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = equipoSeleccionado?.nombre ?: "Seleccionar Equipo",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Equipo Disponible") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        equiposDisponibles.forEach { equipo ->
                            DropdownMenuItem(
                                text = { Text("${equipo.nombre} (${equipo.categoria})") },
                                onClick = {
                                    equipoSeleccionado = equipo
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (solicitante.isNotBlank() && equipoSeleccionado != null) {
                            viewModel.registrarPrestamo(equipoSeleccionado!!, solicitante)
                            solicitante = ""
                            equipoSeleccionado = null
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = solicitante.isNotBlank() && equipoSeleccionado != null
                ) {
                    Text("Registrar Préstamo")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "📋 Historial de Movimientos", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(prestamos) { prestamo ->
                ListItem(
                    headlineContent = { Text(prestamo.solicitante) },
                    supportingContent = {
                        Column {
                            Text("ID Equipo: ${prestamo.equipoId}")
                            Text("Prestado: ${prestamo.fechaPrestamo}")
                            prestamo.fechaDevolucion?.let {
                                Text("Devuelto: $it", color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    },
                    trailingContent = {
                        Row {
                            if (prestamo.fechaDevolucion == null) {
                                IconButton(onClick = { viewModel.registrarDevolucion(prestamo) }) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Devolver",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            IconButton(onClick = { viewModel.eliminar(prestamo) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                )
                HorizontalDivider()
            }
        }

        Button(
            onClick = { navController.navigate("dashboard") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("⬅ Volver")
        }
    }
}
