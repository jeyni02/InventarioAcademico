package ni.edu.uam.inventarioacademico.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DashboardScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "📚 Inventario Académico",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        DashboardCard("📦 Total Equipos", "0")

        DashboardCard("✅ Disponibles", "0")

        DashboardCard("🔄 Prestados", "0")

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navController.navigate("equipos")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("➕ Registrar Equipo")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                navController.navigate("equipos")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📋 Ver Equipos")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                navController.navigate("prestamos")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🤝 Gestionar Préstamos")
        }
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
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = valor,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}