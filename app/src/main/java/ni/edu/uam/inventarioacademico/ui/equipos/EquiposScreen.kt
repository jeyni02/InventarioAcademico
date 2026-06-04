package ni.edu.uam.inventarioacademico.ui.equipos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ni.edu.uam.inventarioacademico.data.local.entity.Equipo
import ni.edu.uam.inventarioacademico.viewmodel.EquipoViewModel

@Composable
fun EquiposScreen(
    navController: NavController,
    viewModel: EquipoViewModel
) {

    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var serie by remember { mutableStateOf("") }

    val equipos by viewModel.equipos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "💻 Gestión de Equipos",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Administra el inventario tecnológico del laboratorio",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 6.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "➕ Registrar Equipo",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del equipo") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Categoría") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = marca,
                    onValueChange = { marca = it },
                    label = { Text("Marca") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = serie,
                    onValueChange = { serie = it },
                    label = { Text("Número de Serie") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {

                        if (
                            nombre.isNotBlank() &&
                            categoria.isNotBlank()
                        ) {

                            viewModel.insertar(
                                Equipo(
                                    nombre = nombre,
                                    categoria = categoria,
                                    marca = marca,
                                    numeroSerie = serie
                                )
                            )

                            nombre = ""
                            categoria = ""
                            marca = ""
                            serie = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Guardar Equipo")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "📋 Equipos Registrados",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (equipos.isEmpty()) {

            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "📭 No hay equipos registrados",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Agrega tu primer equipo usando el formulario superior."
                    )
                }
            }

        } else {

            LazyColumn {

                items(equipos) { equipo ->

                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = "💻 ${equipo.nombre}",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "📂 Categoría: ${equipo.categoria}"
                            )

                            Text(
                                text = "🏢 Marca: ${equipo.marca}"
                            )

                            Text(
                                text = "🔖 Serie: ${equipo.numeroSerie}"
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedButton(
                                onClick = {
                                    viewModel.eliminar(equipo)
                                },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("🗑 Eliminar")
                            }
                        }
                    }
                }

                item {

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )

                    OutlinedButton(
                        onClick = {
                            navController.navigate("dashboard")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("⬅ Volver al Dashboard")
                    }

                    Spacer(
                        modifier = Modifier.height(40.dp)
                    )
                }
            }
        }
    }
}