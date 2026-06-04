package ni.edu.uam.inventarioacademico.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ni.edu.uam.inventarioacademico.data.local.entity.Prestamo

@Dao
interface PrestamoDao {

    @Insert
    suspend fun insertar(prestamo: Prestamo)

    @Update
    suspend fun actualizar(prestamo: Prestamo)

    @Query("SELECT * FROM prestamos")
    fun obtenerTodos(): Flow<List<Prestamo>>
}