package ni.edu.uam.inventarioacademico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ni.edu.uam.inventarioacademico.data.repository.EquipoRepository
import ni.edu.uam.inventarioacademico.data.repository.PrestamoRepository

class ViewModelFactory(
    private val equipoRepository: EquipoRepository,
    private val prestamoRepository: PrestamoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(EquipoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EquipoViewModel(equipoRepository) as T
        }

        if (modelClass.isAssignableFrom(PrestamoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PrestamoViewModel(prestamoRepository) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}