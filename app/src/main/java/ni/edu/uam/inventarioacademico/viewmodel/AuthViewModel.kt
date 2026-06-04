package ni.edu.uam.inventarioacademico.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(user: String, pass: String) {
        // .trim() elimina espacios accidentales y .lowercase() hace que sea insensible a mayúsculas
        val cleanUser = user.trim().lowercase()
        val cleanPass = pass.trim()

        if (cleanUser == "admin" && cleanPass == "1234") {
            _error.value = null
            _isLoggedIn.value = true
        } else {
            _error.value = "Usuario o contraseña incorrectos"
            _isLoggedIn.value = false
        }
    }

    fun logout() {
        _isLoggedIn.value = false
        _error.value = null
    }
}
