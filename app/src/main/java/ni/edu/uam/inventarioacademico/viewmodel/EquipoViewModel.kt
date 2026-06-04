package ni.edu.uam.inventarioacademico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ni.edu.uam.inventarioacademico.data.local.entity.Equipo
import ni.edu.uam.inventarioacademico.data.repository.EquipoRepository

class EquipoViewModel(
    private val repository: EquipoRepository
) : ViewModel() {

    val equipos =
        repository.obtenerTodos()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun insertar(equipo: Equipo) {
        viewModelScope.launch {
            repository.insertar(equipo)
        }
    }

    fun eliminar(equipo: Equipo) {
        viewModelScope.launch {
            repository.eliminar(equipo)
        }
    }
}