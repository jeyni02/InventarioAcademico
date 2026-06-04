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

    private val _prestamos =
        MutableStateFlow<List<Prestamo>>(emptyList())

    val prestamos: StateFlow<List<Prestamo>> =
        _prestamos.asStateFlow()

    init {
        viewModelScope.launch {
            repository.obtenerTodos().collect {
                _prestamos.value = it
            }
        }
    }

    fun insertar(prestamo: Prestamo) {
        viewModelScope.launch {
            repository.insertar(prestamo)
        }
    }
}