package ni.edu.uam.inventarioacademico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import ni.edu.uam.inventarioacademico.data.local.database.AppDatabase
import ni.edu.uam.inventarioacademico.data.repository.EquipoRepository
import ni.edu.uam.inventarioacademico.data.repository.PrestamoRepository
import ni.edu.uam.inventarioacademico.ui.navigation.AppNavigation
import ni.edu.uam.inventarioacademico.ui.theme.InventarioAcademicoTheme
import ni.edu.uam.inventarioacademico.viewmodel.EquipoViewModel
import ni.edu.uam.inventarioacademico.viewmodel.PrestamoViewModel
import ni.edu.uam.inventarioacademico.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Base de datos Room
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "inventario_academico_db"
        ).build()

        // Repositorios
        val equipoRepository = EquipoRepository(
            database.equipoDao()
        )

        val prestamoRepository = PrestamoRepository(
            database.prestamoDao()
        )

        // Factory
        val factory = ViewModelFactory(
            equipoRepository,
            prestamoRepository
        )

        // ViewModels
        val equipoViewModel = ViewModelProvider(
            this,
            factory
        )[EquipoViewModel::class.java]

        val prestamoViewModel = ViewModelProvider(
            this,
            factory
        )[PrestamoViewModel::class.java]

        setContent {

            InventarioAcademicoTheme {

                AppNavigation(
                    equipoViewModel = equipoViewModel,
                    prestamoViewModel = prestamoViewModel
                )
            }
        }
    }
}