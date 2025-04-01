package com.example.oportunia.presentation.ui.viewmodel



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.data.repository.RemoteUsersRepository
import com.example.oportunia.domain.model.Users
import com.example.oportunia.domain.repository.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate


sealed class UsersState {
    data object Loading : UsersState()
    data class Success(val user: Users) : UsersState()
    data object Empty : UsersState()
    data class Error(val message: String) : UsersState()
}


class UsersViewModel(
    private val repository: UsersRepository,
    private val remoteRepository: RemoteUsersRepository

) : ViewModel() {

    private val _userState = MutableStateFlow<UsersState>(UsersState.Empty)
    val userState: StateFlow<UsersState> = _userState

    private val _selectedUser = MutableStateFlow<Users?>(null)
    val selectedUser: StateFlow<Users?> = _selectedUser // por que no se usa???

    private val _userList = MutableStateFlow<List<Users>>(emptyList())
    val userList: StateFlow<List<Users>> = _userList

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

    fun findAllUsers() {
        viewModelScope.launch {
            repository.findAllUsers()
                .onSuccess { users ->

                    _userList.value = users
                }
                .onFailure { exception ->
                    Log.e("UserViewModel", "no se econtro nada")
                }
        }
    }

    fun validateUserCredentials(correo: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _userState.value = UsersState.Loading

            repository.findUserByEmail(correo)
                .onSuccess { user ->
                    val isValid = user.password == password
                    if (isValid) {
                        _userState.value = UsersState.Success(user)
                        _selectedUser.value = user


                        onResult(true)
                    } else {
                        _userState.value = UsersState.Error("Contraseña incorrecta")
                        onResult(false)
                    }
                }
                .onFailure { exception ->
                    _userState.value = UsersState.Error("Usuario no encontrado: ${exception.message}")
                    onResult(false)
                }
        }
    }


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

    fun probarConexionConMockApi() {
        viewModelScope.launch {
            val users = remoteRepository.getAllUsers()
            if (users != null) {
                Log.d("MockAPI", "Se recibieron ${users.size} usuarios desde la API.")
                users.forEach {
                    Log.d("MockAPI", "Usuario → ID: ${it.id}, Email: ${it.email}, RoleID: ${it.roleId}")
                }
            } else {
                Log.e("MockAPI", "Error al conectar con la API.")
            }
        }
    }






}
