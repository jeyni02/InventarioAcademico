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

    private val _equipos =
        MutableStateFlow<List<Equipo>>(emptyList())

    val equipos: StateFlow<List<Equipo>> =
        _equipos.asStateFlow()

    init {
        viewModelScope.launch {
            repository.obtenerTodos().collect {
                _equipos.value = it
            }
        }
    }

    fun insertar(equipo: Equipo) {
        viewModelScope.launch {
            repository.insertar(equipo)
        }
    }
}