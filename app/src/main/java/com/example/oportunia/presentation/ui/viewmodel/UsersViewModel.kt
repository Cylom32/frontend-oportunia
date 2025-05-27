package com.example.oportunia.presentation.ui.viewmodel



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.data.remote.dto.AuthResponseDto
import com.example.oportunia.domain.model.AuthResult
import com.example.oportunia.domain.model.Users
import com.example.oportunia.domain.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


sealed class UsersState  {
    data object Loading : UsersState()
    data class Success(val user: Users) : UsersState()
    data object Empty : UsersState()
    data class Error(val message: String) : UsersState()
}

// en UsersViewModel.kt o donde defines AuthState
sealed class AuthState {
    object Loading                   : AuthState()
    object Empty                     : AuthState()
    data class Success(val auth: AuthResult) : AuthState()
    data class Error(val message: String)      : AuthState()
}


@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UsersRepository

) : ViewModel() {

    private val _userState = MutableStateFlow<UsersState>(UsersState.Empty)
    val userState: StateFlow<UsersState> = _userState

    private val _selectedUser = MutableStateFlow<Users?>(null)
    val selectedUser: StateFlow<Users?> = _selectedUser // por que no se usa???

    private val _userList = MutableStateFlow<List<Users>>(emptyList())
    val userList: StateFlow<List<Users>> = _userList



    private val _authState = MutableStateFlow<AuthState>(AuthState.Empty)
    val authState: StateFlow<AuthState> = _authState



    fun selectUserById(userId: Int) {
        viewModelScope.launch {
            repository.findUserById(userId)
                .onSuccess { user ->
                    _selectedUser.value = user
                }
                .onFailure { exception ->
                    Log.e("UserViewModel", "Error fetching user by ID: ${exception.message}")
                }
        }
    }

    fun selectedUserIdValue(): Int{
       return  selectedUser.value!!.id
    }



//    fun findAllUsers() {
//        Log.d("UserDebug", "🔍 findAllUsers() fue invocado")
//
//        viewModelScope.launch {
//            repository.findAllUsers()
//                .onSuccess { users ->
//                    Log.d("UserDebug", "✅ Usuarios recibidos: ${users.size}")
//                    _userList.value = users
//                }
//                .onFailure { exception ->
//                    Log.e("UserDebug", "❌ Error al traer usuarios: ${exception.message}")
//                }
//        }
//    }



//    fun validateUserCredentials(correo: String, password: String, onResult: (Boolean) -> Unit) {
//        viewModelScope.launch {
//            _userState.value = UsersState.Loading
//
//            repository.loginUser(correo, password)
//                .onSuccess { user ->
//                    _userState.value = UsersState.Success(user)
//                    _selectedUser.value = user
//
//                    // Imprimir todos los datos del usuario autenticado
//                    Log.d("UsersViewModel", "✅ Usuario logueado:")
//                    Log.d("UsersViewModel", "ID: ${user.id}")
//                    Log.d("UsersViewModel", "Email: ${user.email}")
//                    Log.d("UsersViewModel", "Password: ${user.password}")
//                    Log.d("UsersViewModel", "Img: ${user.img}")
//                    Log.d("UsersViewModel", "Fecha: ${user.creationDate}")
//                    Log.d("UsersViewModel", "Rol: ${user.roleId}")
//
//                    onResult(true)
//                }
//                .onFailure { exception ->
//                    _userState.value = UsersState.Error("Credenciales inválidas: ${exception.message}")
//                    onResult(false)
//                }
//        }
//    }




//    fun loginTest() {
//        val email = "seanwade@gallagher.com"
//        val password = "@(mb0k!s7I"
//
//        viewModelScope.launch {
//            try {
//                val response = repository.loginUser(email, password)
//                response
//                    .onSuccess { user ->
//                        println("Usuario encontrado: ${user.email}")
//                    }
//                    .onFailure {
//                        println("redenciales inválidas: ${it.message}")
//                    }
//            } catch (e: Exception) {
//                println("irror en la petición: ${e.message}")
//            }
//        }
//    }





    fun getAuthenticatedUserId(): Int? {
        return (userState.value as? UsersState.Success)?.user?.id


    }

    fun getNextId() : Int {
        return _userList.value.maxOfOrNull { it.id }?.plus(1) ?: 1
    }

    fun saveUser(id: Int, email: String, password: String, roleId: Int?) {
        val user = Users(
            id = id,
            email = email,
            password = password,
            img = null,
            creationDate = LocalDate.now(),
            roleId = roleId
        )

        viewModelScope.launch {
            repository.saveUser(user)
            Log.d("UsersViewModel", "Usuario guardado: $user")
        }
    }


    fun logout() {
        viewModelScope.launch {
            _userState.value = UsersState.Empty
            _selectedUser.value = null
            Log.d("UsersViewModel", "Sesión cerrada correctamente")
        }
    }



    fun login(
        correo: String,
        password: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            repository.loginUser(correo, password)
                .onSuccess { authResponse ->
                    _authState.value = AuthState.Success(authResponse)
                    Log.d("LoginDebug", "AuthResponse recibido: $authResponse")
                    onResult(true)
                }
                .onFailure { ex ->
                    _authState.value = AuthState.Error("Credenciales inválidas: ${ex.message}")
                    onResult(false)
                }
        }
    }

    // Si necesitas obtener el token o el userId en otras partes:
    fun getToken(): String? =
        (authState.value as? AuthState.Success)?.auth?.token

    fun getUserId(): Int? =
        (authState.value as? AuthState.Success)
            ?.auth
            ?.email
            ?.toIntOrNull()



}
