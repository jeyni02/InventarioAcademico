package ni.edu.uam.inventarioacademico.data.repository

import kotlinx.coroutines.flow.Flow
import ni.edu.uam.inventarioacademico.data.local.dao.EquipoDao
import ni.edu.uam.inventarioacademico.data.local.dao.PrestamoDao
import ni.edu.uam.inventarioacademico.data.local.entity.Equipo
import ni.edu.uam.inventarioacademico.data.local.entity.Prestamo
import java.text.SimpleDateFormat
import java.util.*

class PrestamoRepository(
    private val prestamoDao: PrestamoDao,
    private val equipoDao: EquipoDao
) {

    fun obtenerTodos(): Flow<List<Prestamo>> = prestamoDao.obtenerTodos()

    suspend fun registrarPrestamo(prestamo: Prestamo, equipo: Equipo) {
        prestamoDao.insertar(prestamo)
        equipoDao.actualizar(equipo.copy(disponible = false))
    }

    suspend fun registrarDevolucion(prestamo: Prestamo) {
        val equipo = equipoDao.obtenerPorId(prestamo.equipoId)
        if (equipo != null) {
            val fechaActual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
            prestamoDao.actualizar(prestamo.copy(fechaDevolucion = fechaActual))
            equipoDao.actualizar(equipo.copy(disponible = true))
        }
    }

    fun obtenerEquiposDisponibles(): Flow<List<Equipo>> = equipoDao.obtenerDisponibles()

    suspend fun eliminar(prestamo: Prestamo) {
        prestamoDao.eliminar(prestamo)
    }
}
