package ni.edu.uam.inventarioacademico.data.repository

import kotlinx.coroutines.flow.Flow
import ni.edu.uam.inventarioacademico.data.local.dao.EquipoDao
import ni.edu.uam.inventarioacademico.data.local.entity.Equipo

class EquipoRepository(
    private val equipoDao: EquipoDao
) {

    fun obtenerTodos(): Flow<List<Equipo>> =
        equipoDao.obtenerTodos()

    fun contarEquipos(): Flow<Int> =
        equipoDao.contarEquipos()

    fun contarDisponibles(): Flow<Int> =
        equipoDao.contarDisponibles()

    fun contarPrestados(): Flow<Int> =
        equipoDao.contarPrestados()

    suspend fun insertar(equipo: Equipo) =
        equipoDao.insertar(equipo)

    suspend fun actualizar(equipo: Equipo) =
        equipoDao.actualizar(equipo)

    suspend fun eliminar(equipo: Equipo) =
        equipoDao.eliminar(equipo)
}