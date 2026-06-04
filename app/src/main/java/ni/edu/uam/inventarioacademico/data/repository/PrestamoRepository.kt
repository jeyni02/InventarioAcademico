package ni.edu.uam.inventarioacademico.data.repository

import kotlinx.coroutines.flow.Flow
import ni.edu.uam.inventarioacademico.data.local.dao.PrestamoDao
import ni.edu.uam.inventarioacademico.data.local.entity.Prestamo

class PrestamoRepository(
    private val prestamoDao: PrestamoDao
) {

    fun obtenerTodos(): Flow<List<Prestamo>> {
        return prestamoDao.obtenerTodos()
    }

    suspend fun insertar(prestamo: Prestamo) {
        prestamoDao.insertar(prestamo)
    }
}