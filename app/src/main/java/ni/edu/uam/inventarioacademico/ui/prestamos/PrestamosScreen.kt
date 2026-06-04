package ni.edu.uam.inventarioacademico.ui.prestamos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ni.edu.uam.inventarioacademico.data.local.entity.Prestamo
import ni.edu.uam.inventarioacademico.viewmodel.PrestamoViewModel

@Composable
fun PrestamosScreen(
    navController: NavController,
    viewModel: PrestamoViewModel
) {

    var solicitante by remember {
        mutableStateOf("")
    }

    val prestamos by viewModel.prestamos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "📦 Gestión de Préstamos",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Registro de préstamos del laboratorio",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        ElevatedCard {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                OutlinedTextField(
                    value = solicitante,
                    onValueChange = {
                        solicitante = it
                    },
                    label = {
                        Text("Solicitante")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {

                        if (solicitante.isNotBlank()) {

                            viewModel.insertar(
                                Prestamo(
                                    equipoId = 1,
                                    solicitante = solicitante,
                                    fechaPrestamo = System.currentTimeMillis().toString(),
                                    fechaDevolucion = null
                                )
                            )

                            solicitante = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Registrar Préstamo")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "📋 Historial de Préstamos",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {

            items(prestamos) { prestamo ->

                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "👤 ${prestamo.solicitante}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "ID Equipo: ${prestamo.equipoId}"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedButton(
                            onClick = {
                                viewModel.eliminar(prestamo)
                            }
                        ) {
                            Text("🗑 Eliminar")
                        }
                    }
                }
            }
        }
    }
}