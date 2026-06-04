package ni.edu.uam.inventarioacademico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ni.edu.uam.inventarioacademico.data.local.entity.Equipo
import ni.edu.uam.inventarioacademico.data.local.entity.Prestamo
import ni.edu.uam.inventarioacademico.data.repository.PrestamoRepository

class PrestamoViewModel(
    private val repository: PrestamoRepository
) : ViewModel() {

    val prestamos: StateFlow<List<Prestamo>> = repository.obtenerTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val equiposDisponibles: StateFlow<List<Equipo>> = repository.obtenerEquiposDisponibles()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun registrarPrestamo(equipo: Equipo, solicitante: String) {
        viewModelScope.launch {
            val fechaActual = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
            val prestamo = Prestamo(
                equipoId = equipo.id,
                solicitante = solicitante,
                fechaPrestamo = fechaActual
            )
            repository.registrarPrestamo(prestamo, equipo)
        }
    }

    fun registrarDevolucion(prestamo: Prestamo) {
        viewModelScope.launch {
            repository.registrarDevolucion(prestamo)
        }
    }

    fun eliminar(prestamo: Prestamo) {
        viewModelScope.launch {
            repository.eliminar(prestamo)
        }
    }
}
