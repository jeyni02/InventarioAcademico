package ni.edu.uam.inventarioacademico.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CampoTextoPersonalizado(
    valor: String,
    etiqueta: String,
    onValorChange: (String) -> Unit
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        label = {
            Text(text = etiqueta)
        },
        modifier = Modifier.fillMaxWidth()
    )
}