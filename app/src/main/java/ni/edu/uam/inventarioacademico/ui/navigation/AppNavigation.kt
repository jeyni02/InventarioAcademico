package ni.edu.uam.inventarioacademico.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ni.edu.uam.inventarioacademico.ui.auth.LoginScreen
import ni.edu.uam.inventarioacademico.ui.dashboard.DashboardScreen
import ni.edu.uam.inventarioacademico.ui.equipos.EquiposScreen
import ni.edu.uam.inventarioacademico.ui.prestamos.PrestamosScreen
import ni.edu.uam.inventarioacademico.viewmodel.AuthViewModel
import ni.edu.uam.inventarioacademico.viewmodel.EquipoViewModel
import ni.edu.uam.inventarioacademico.viewmodel.PrestamoViewModel

@Composable
fun AppNavigation(
    equipoViewModel: EquipoViewModel,
    prestamoViewModel: PrestamoViewModel,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("dashboard") {
            DashboardScreen(
                navController = navController,
                equipoViewModel = equipoViewModel
            )
        }

        composable("equipos") {
            EquiposScreen(
                navController = navController,
                viewModel = equipoViewModel
            )
        }

        composable("prestamos") {
            PrestamosScreen(
                navController = navController,
                viewModel = prestamoViewModel
            )
        }
    }
}
