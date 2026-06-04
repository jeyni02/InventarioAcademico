package ni.edu.uam.inventarioacademico.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ni.edu.uam.inventarioacademico.data.local.entity.Equipo

@Composable
fun TarjetaEquipo(
    equipo: Equipo
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text("Nombre: ${equipo.nombre}")

            Text("Marca: ${equipo.marca}")

            Text("Categoría: ${equipo.categoria}")

            Text("Serie: ${equipo.numeroSerie}")

            Text(
                if (equipo.disponible)
                    "Disponible"
                else
                    "Prestado"
            )
        }
    }
}