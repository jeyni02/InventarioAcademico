package ni.edu.uam.inventarioacademico.ui.equipos

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ni.edu.uam.inventarioacademico.data.local.entity.Equipo
import ni.edu.uam.inventarioacademico.viewmodel.EquipoViewModel
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquiposScreen(
    navController: NavController,
    viewModel: EquipoViewModel
) {
    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var serie by remember { mutableStateOf("") }
    var equipoAEditar by remember { mutableStateOf<Equipo?>(null) }

    val busqueda by viewModel.busqueda.collectAsState()
    val filtroCategoria by viewModel.filtroCategoria.collectAsState()
    val equipos by viewModel.equipos.collectAsState()
    val categoriasDisponibles by viewModel.categorias.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(equipoAEditar) {
        equipoAEditar?.let {
            nombre = it.nombre
            categoria = it.categoria
            marca = it.marca
            serie = it.numeroSerie
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "💻 Gestión de Equipos",
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(onClick = {
                val csv = viewModel.generarCSV()
                guardarCSV(context, csv)
            }) {
                Icon(Icons.Default.Share, contentDescription = "Exportar CSV")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Buscador
        OutlinedTextField(
            value = busqueda,
            onValueChange = { viewModel.actualizarBusqueda(it) },
            label = { Text("Buscar por nombre o serie") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Filtro de Categorías
        ScrollableTabRow(
            selectedTabIndex = categoriasDisponibles.indexOf(filtroCategoria).coerceAtLeast(0),
            edgePadding = 0.dp,
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            divider = {}
        ) {
            categoriasDisponibles.forEach { cat ->
                FilterChip(
                    selected = filtroCategoria == cat,
                    onClick = { viewModel.actualizarFiltroCategoria(cat) },
                    label = { Text(cat) },
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = if (equipoAEditar == null) "➕ Nuevo Registro" else "✏️ Editar Registro",
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    OutlinedTextField(
                        value = categoria,
                        onValueChange = { categoria = it },
                        label = { Text("Categoría") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = marca,
                        onValueChange = { marca = it },
                        label = { Text("Marca") },
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = serie,
                    onValueChange = { serie = it },
                    label = { Text("Número de Serie") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            if (nombre.isNotBlank() && categoria.isNotBlank()) {
                                if (equipoAEditar == null) {
                                    viewModel.insertar(Equipo(nombre = nombre, categoria = categoria, marca = marca, numeroSerie = serie))
                                } else {
                                    viewModel.actualizar(equipoAEditar!!.copy(nombre = nombre, categoria = categoria, marca = marca, numeroSerie = serie))
                                    equipoAEditar = null
                                }
                                nombre = ""; categoria = ""; marca = ""; serie = ""
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (equipoAEditar == null) "Guardar" else "Actualizar")
                    }

                    if (equipoAEditar != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(onClick = {
                            equipoAEditar = null
                            nombre = ""; categoria = ""; marca = ""; serie = ""
                        }) {
                            Text("Cancelar")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(equipos) { equipo ->
                ListItem(
                    headlineContent = { Text(equipo.nombre) },
                    supportingContent = { Text("${equipo.categoria} • SN: ${equipo.numeroSerie} • ${if (equipo.disponible) "✅" else "🔄"}") },
                    trailingContent = {
                        Row {
                            IconButton(onClick = { equipoAEditar = equipo }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(onClick = { viewModel.eliminar(equipo) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                )
                HorizontalDivider()
            }
        }

        Button(
            onClick = { navController.navigate("dashboard") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("⬅ Volver")
        }
    }
}

private fun guardarCSV(context: Context, contenido: String) {
    try {
        val fileName = "inventario_equipos.csv"
        val file = File(context.getExternalFilesDir(null), fileName)
        FileOutputStream(file).use { outputStream ->
            outputStream.write(contenido.toByteArray())
        }
        Toast.makeText(context, "Exportado a: ${file.absolutePath}", Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Error al exportar: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}
