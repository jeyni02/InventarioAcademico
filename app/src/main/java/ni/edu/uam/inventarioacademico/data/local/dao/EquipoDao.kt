package ni.edu.uam.inventarioacademico.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ni.edu.uam.inventarioacademico.data.local.entity.Equipo

@Dao
interface EquipoDao {

    @Insert
    suspend fun insertar(equipo: Equipo)

    @Update
    suspend fun actualizar(equipo: Equipo)

    @Delete
    suspend fun eliminar(equipo: Equipo)

    @Query("SELECT * FROM equipos")
    fun obtenerTodos(): Flow<List<Equipo>>

    @Query("SELECT * FROM equipos WHERE disponible = 1")
    fun obtenerDisponibles(): Flow<List<Equipo>>
}