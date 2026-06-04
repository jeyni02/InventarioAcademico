package ni.edu.uam.inventarioacademico.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ni.edu.uam.inventarioacademico.data.local.dao.EquipoDao
import ni.edu.uam.inventarioacademico.data.local.dao.PrestamoDao
import ni.edu.uam.inventarioacademico.data.local.entity.Equipo
import ni.edu.uam.inventarioacademico.data.local.entity.Prestamo

@Database(
    entities = [
        Equipo::class,
        Prestamo::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun equipoDao(): EquipoDao

    abstract fun prestamoDao(): PrestamoDao
}