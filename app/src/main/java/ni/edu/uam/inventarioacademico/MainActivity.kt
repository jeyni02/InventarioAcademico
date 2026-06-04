package ni.edu.uam.inventarioacademico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ni.edu.uam.inventarioacademico.ui.navigation.AppNavigation
import ni.edu.uam.inventarioacademico.ui.theme.InventarioAcademicoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            InventarioAcademicoTheme {

                AppNavigation()
            }
        }
    }
}