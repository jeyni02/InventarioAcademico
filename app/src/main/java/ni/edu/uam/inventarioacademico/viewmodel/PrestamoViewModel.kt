package ni.edu.uam.inventarioacademico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ni.edu.uam.inventarioacademico.data.local.entity.Prestamo
import ni.edu.uam.inventarioacademico.data.repository.PrestamoRepository

class PrestamoViewModel(
    private val repository: PrestamoRepository
) : ViewModel() {

    val prestamos =
        repository.obtenerTodos()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun insertar(prestamo: Prestamo) {
        viewModelScope.launch {
            repository.insertar(prestamo)
        }
    }

    fun eliminar(prestamo: Prestamo) {
        viewModelScope.launch {
            repository.eliminar(prestamo)
        }
    }
}