package ni.edu.uam.inventarioacademico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ni.edu.uam.inventarioacademico.data.local.entity.Equipo
import ni.edu.uam.inventarioacademico.data.repository.EquipoRepository

class EquipoViewModel(
    private val repository: EquipoRepository
) : ViewModel() {

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda

    private val _filtroCategoria = MutableStateFlow("Todas")
    val filtroCategoria: StateFlow<String> = _filtroCategoria

    val equipos: StateFlow<List<Equipo>> = combine(
        repository.obtenerTodos(),
        _busqueda,
        _filtroCategoria
    ) { lista, query, categoria ->
        lista.filter { equipo ->
            val coincideBusqueda = equipo.nombre.contains(query, ignoreCase = true) ||
                    equipo.numeroSerie.contains(query, ignoreCase = true)
            val coincideCategoria = categoria == "Todas" || equipo.categoria == categoria
            coincideBusqueda && coincideCategoria
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val categorias: StateFlow<List<String>> = repository.obtenerTodos()
        .map { lista ->
            listOf("Todas") + lista.map { it.categoria }.distinct().sorted()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf("Todas")
        )

    val totalEquipos: StateFlow<Int> = repository.contarEquipos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val disponibles: StateFlow<Int> = repository.contarDisponibles()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val prestados: StateFlow<Int> = repository.contarPrestados()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val categoriaPrincipal: StateFlow<String> = repository.obtenerTodos()
        .map { lista ->
            if (lista.isEmpty()) "Ninguna"
            else lista.groupBy { it.categoria }
                .maxByOrNull { it.value.size }?.key ?: "Ninguna"
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "Cargando..."
        )

    val estadisticasCategorias: StateFlow<Map<String, Int>> = repository.obtenerTodos()
        .map { lista ->
            lista.groupBy { it.categoria }.mapValues { it.value.size }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyMap()
        )

    fun actualizarBusqueda(nuevaBusqueda: String) {
        _busqueda.value = nuevaBusqueda
    }

    fun actualizarFiltroCategoria(nuevaCategoria: String) {
        _filtroCategoria.value = nuevaCategoria
    }

    fun insertar(equipo: Equipo) {
        viewModelScope.launch {
            repository.insertar(equipo)
        }
    }

    fun actualizar(equipo: Equipo) {
        viewModelScope.launch {
            repository.actualizar(equipo)
        }
    }

    fun eliminar(equipo: Equipo) {
        viewModelScope.launch {
            repository.eliminar(equipo)
        }
    }

    fun generarCSV(): String {
        val sb = StringBuilder()
        sb.append("ID,Nombre,Categoria,Marca,Serie,Disponible\n")
        equipos.value.forEach {
            sb.append("${it.id},${it.nombre},${it.categoria},${it.marca},${it.numeroSerie},${it.disponible}\n")
        }
        return sb.toString()
    }
}
