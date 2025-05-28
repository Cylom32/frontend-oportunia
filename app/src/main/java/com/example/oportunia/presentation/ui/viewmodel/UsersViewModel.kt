package com.example.oportunia.presentation.ui.viewmodel



import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.data.remote.dto.AuthResponseDto
import com.example.oportunia.data.remote.dto.UserEmailResponse
import com.example.oportunia.domain.model.AuthResult
import com.example.oportunia.domain.model.University
import com.example.oportunia.domain.model.Users
import com.example.oportunia.domain.repository.UniversityRepository
import com.example.oportunia.domain.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject



import org.mindrot.jbcrypt.BCrypt




import androidx.lifecycle.viewModelScope
import com.example.oportunia.data.remote.dto.StudentResult
import com.example.oportunia.data.remote.dto.UserWhitoutId
import kotlinx.coroutines.launch

import com.example.oportunia.data.remote.dto.UsersDTO
import retrofit2.Response


sealed class UsersState  {
    data object Loading : UsersState()
    data class Success(val user: Users) : UsersState()
    data object Empty : UsersState()
    data class Error(val message: String) : UsersState()
}


sealed class AuthState {
    object Loading                   : AuthState()
    object Empty                     : AuthState()
    data class Success(val auth: AuthResult) : AuthState()
    data class Error(val message: String)      : AuthState()
}


@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UsersRepository,
    private val universityRepository: UniversityRepository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {





    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val KEY_USER_ID = "user_id"
    }

    private val prefs = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun persistUserId(id: Int) {
        prefs.edit().putInt(KEY_USER_ID, id).apply()
    }



    private val _userState = MutableStateFlow<UsersState>(UsersState.Empty)
    val userState: StateFlow<UsersState> = _userState

    private val _selectedUser = MutableStateFlow<Users?>(null)
    val selectedUser: StateFlow<Users?> = _selectedUser // por que no se usa???

    private val _userList = MutableStateFlow<List<Users>>(emptyList())
    val userList: StateFlow<List<Users>> = _userList



    private val _authState = MutableStateFlow<AuthState>(AuthState.Empty)
    val authState: StateFlow<AuthState> = _authState


    // Estado para exponer el UserEmailResponse crudo
    private val _userByEmail = MutableStateFlow<UserEmailResponse?>(null)
    val userByEmail: StateFlow<UserEmailResponse?> = _userByEmail

    // Estado para errores al buscar por email
    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    ///////////////////////            PARA UNIVERSIDADES             /////////////////////////



    private val _universities = MutableStateFlow<List<University>>(emptyList())
    val universities: StateFlow<List<University>> = _universities



    private val _universityError = MutableStateFlow<String?>(null)
    val universityError: StateFlow<String?> = _universityError


    //////////////////////////////////////////////////////////////////////////////////////////








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
           // Log.d("UsersViewModel", "Usuario guardado: $user")
        }
    }


    fun logout() {
        viewModelScope.launch {
            _userState.value = UsersState.Empty
            _selectedUser.value = null
           // Log.d("UsersViewModel", "Sesión cerrada correctamente")
        }
    }


    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token


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

                    // Guardamos el token en la variable
                    _token.value = authResponse.token

                    Log.d("LoginDebug", "TOKEN completo → ${authResponse.token}")
                    Log.d("LoginDebug", "TOKEN completo → ${_token.value}")
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









    private val _userId = MutableStateFlow<Int?>(null)
    val userId: StateFlow<Int?> = _userId


    /**
     * Llama a repository.findUserByEmail(email) y publica el DTO o el error.
     */
    fun fetchUserByEmail(
        email: String,
        onResult: (Int?) -> Unit
    ) = viewModelScope.launch {
        Log.d("UsersVM", "🔎 fetchUserByEmail iniciada para: $email")
        repository.findUserByEmail(email)
            .onSuccess { dto ->
                Log.d("UsersVM", "✅ Usuario encontrado: ${dto.email} → idUser=${dto.idUser}")
                _userByEmail.value = dto
                _userId.value      = dto.idUser
                persistUserId(dto.idUser)
                onResult(dto.idUser)
            }
            .onFailure { err ->
                Log.e("UsersVM", "❌ fetchUserByEmail fallo para $email: ${err.message}")
                _userId.value = null
                onResult(null)
            }
    }


    fun printIdUser(): Int? {
        val id = prefs.getInt(KEY_USER_ID, -1)
      //  Log.d("UsersViewModel", "id_user: $id")  // interpolación correcta
        return if (id != -1) id else null
    }


//    fun printIdUser(){
//        Log.d("UsersViewModel", "id_user: ${userId.value}")
//    }



    //////////////////////////////////           PARA UNIVERSIDADES            //////////////////////////////////


    // en UsersViewModel.kt
    fun fetchUniversities() = viewModelScope.launch {
        Log.d("UsersViewModel", "👉 fetchUniversities() arrancó")  // <- añade esto

        _universityError.value = null

        universityRepository
            .findAllUniversities()   // sin parámetro
            .onSuccess { list ->
                _universities.value = list
                Log.d("UsersViewModel", "✅ recibí ${list.size} universidades")
            }
            .onFailure { ex ->
                _universityError.value = ex.message
                Log.e("UsersViewModel", "❌ Error: ${ex.message}")
            }
    }


    /////////////////////  --------------- SETEANDO VARIABLES PARA EL REGISTRO  -----------------////////////////////


    //////////////////////////////////  ESTADOS PARA DATOS DE REGISTRO  ////////////////////////////////

    // Nombre
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre
    fun setNombre(value: String) { _nombre.value = value }

    // Primer apellido
    private val _primerApellido = MutableStateFlow("")
    val primerApellido: StateFlow<String> = _primerApellido
    fun setPrimerApellido(value: String) { _primerApellido.value = value }

    // Segundo apellido
    private val _segundoApellido = MutableStateFlow("")
    val segundoApellido: StateFlow<String> = _segundoApellido
    fun setSegundoApellido(value: String) { _segundoApellido.value = value }

    // Universidad seleccionada (nombre)
    private val _selectedUniversityName = MutableStateFlow("")
    val selectedUniversityName: StateFlow<String> = _selectedUniversityName
    fun setSelectedUniversityName(value: String) { _selectedUniversityName.value = value }

    // Universidad seleccionada (ID)
    private val _selectedUniversityId = MutableStateFlow(0)
    val selectedUniversityId: StateFlow<Int> = _selectedUniversityId
    fun setSelectedUniversityId(value: Int) { _selectedUniversityId.value = value }
/////////////////////        ESTADOS PARA CORREO Y CONTRASEÑA      ///////////////////////////////////

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    fun setEmail(value: String) { _email.value = value }

    // Contraseña
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password
    fun setPassword(value: String) { _password.value = value }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    fun saveAndLogRegistration(
        nombre: String,
        primerAp: String,
        segundoAp: String,
        uniName: String,
        uniId: Int
    ) {
        // 1) setea con los setters públicos
        setNombre(nombre)
        setPrimerApellido(primerAp)
        setSegundoApellido(segundoAp)
        setSelectedUniversityName(uniName)
        setSelectedUniversityId(uniId)

        // 2) loggea inmediatamente
        Log.d("Registro", buildString {
            append("Universidad: ")
            append(uniName)
            append(" (ID=$uniId)\n")
            append("Nombre completo: ")
            append(nombre).append(" ")
            append(primerAp).append(" ")
            append(segundoAp)
        })
    }





    //////////////////////////////////           PARA UNIVERSIDADES            //////////////////////////////////


    private val _passwordHash = MutableStateFlow("")
    val passwordHash: StateFlow<String> = _passwordHash
    private fun setPasswordHash(value: String) { _passwordHash.value = value }

    fun registerUserWithHashedPassword(rawEmail: String, rawPassword: String) {
        // Guarda el email
        setEmail(rawEmail)

        // Genera salt de coste 12 y hash
        val salt = BCrypt.gensalt(12)
        val hash = BCrypt.hashpw(rawPassword, salt)

        // Guarda el hash
        setPasswordHash(hash)
    }




    fun createUserWithoutId(
        rawEmail: String,
        rawPassword: String,             // texto plano
        img: String? = "gabriel.jpg",
        roleId: Int = 1
    ) {
        viewModelScope.launch {
            // 1) Genera el hash con coste 12
            val salt = BCrypt.gensalt(12)
            val hashed = BCrypt.hashpw(rawPassword, salt)
            Log.d("UsersVM", "🔐 Contraseña hasheada: $hashed")

            // 2) Construye el modelo de dominio sin id
            val user = UserWhitoutId(
                email        = rawEmail,
                password     = hashed,
                img          = img,
                creationDate = LocalDate.now().toString(),
                idRole       = roleId
            )

            // 3) Llama al repositorio y obtiene el domain model de vuelta
            val result: kotlin.Result<Users> = repository.saveUserNoId(user)

            // 4) Manejo de la respuesta
            result
                .onSuccess { created ->
                    Log.d("UsersVM", "✅ Usuario creado: $created")
                }
                .onFailure { error ->
                    Log.e("UsersVM", "❌ Error al crear usuario", error)
                }
        }
    }






    private val _student = MutableStateFlow<StudentResult?>(null)
    val student: StateFlow<StudentResult?> = _student



//    fun fetchStudentByUserId(userId: Int, onResult: (Boolean) -> Unit) {
//        viewModelScope.launch {
//
//            val authToken = token.value
//            if (authToken.isNullOrBlank()) {
//                onResult(false)
//                return@launch
//            }
//
//            // 2) Llama al repositorio
//            repository
//                .getStudentByUserId("Bearer $authToken", userId)
//                .onSuccess { studentResult ->
//                    // 3) Emite el StudentResult en tu StateFlow / LiveData que ya hayas declarado
//                    _student.value = studentResult
//                    onResult(true)
//                }
//                .onFailure { ex ->
//                    Log.e("UserViewModel", "Error fetching student", ex)
//                    onResult(false)
//                }
//        }
//    }
//
//




}