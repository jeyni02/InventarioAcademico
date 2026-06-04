package ni.edu.uam.inventarioacademico.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ni.edu.uam.inventarioacademico.viewmodel.EquipoViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
    equipoViewModel: EquipoViewModel
) {
    val total by equipoViewModel.totalEquipos.collectAsState()
    val disponibles by equipoViewModel.disponibles.collectAsState()
    val prestados by equipoViewModel.prestados.collectAsState()
    val categoriaTop by equipoViewModel.categoriaPrincipal.collectAsState()
    val estadisticas by equipoViewModel.estadisticasCategorias.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "🎓 Inventario Académico",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f)) {
                DashboardCard("📦 Total", total.toString())
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(1f)) {
                DashboardCard("✅ Libres", disponibles.toString())
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f)) {
                DashboardCard("🔄 Prestados", prestados.toString())
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(1f)) {
                DashboardCard("📊 Top Cat.", categoriaTop)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "📈 Estadísticas por Categoría", style = MaterialTheme.typography.titleMedium)
        
        Spacer(modifier = Modifier.height(12.dp))
        
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (estadisticas.isEmpty()) {
                    Text("No hay datos para mostrar gráficos", style = MaterialTheme.typography.bodySmall)
                } else {
                    val maxVal = estadisticas.values.maxOrNull() ?: 1
                    estadisticas.forEach { (cat, count) ->
                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = cat, style = MaterialTheme.typography.labelMedium)
                                Text(text = count.toString(), style = MaterialTheme.typography.labelMedium)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(count.toFloat() / maxVal)
                                    .height(12.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("equipos") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("💻 Gestionar Equipos")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { navController.navigate("prestamos") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("🤝 Gestionar Préstamos")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun DashboardCard(
    titulo: String,
    valor: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = valor,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
