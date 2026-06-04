package ni.edu.uam.inventarioacademico.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ni.edu.uam.inventarioacademico.ui.dashboard.DashboardScreen
import ni.edu.uam.inventarioacademico.ui.equipos.EquiposScreen
import ni.edu.uam.inventarioacademico.ui.prestamos.PrestamosScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "dashboard"
    ) {

        composable("dashboard") {
            DashboardScreen(navController)
        }

        composable("equipos") {
            EquiposScreen(navController)
        }

        composable("prestamos") {
            PrestamosScreen(navController)
        }
    }
}