package com.example.oportunia.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.domain.model.Student
import com.example.oportunia.domain.model.Users
import com.example.oportunia.domain.repository.StudentRepository
import com.example.oportunia.domain.repository.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class StudentState {
    data object Loading : StudentState()
    data class Success(val student: Student) : StudentState()
    data object Empty : StudentState()
    data class Error(val message: String) : StudentState()
}


class StudentViewModel(
    private val repository: StudentRepository
) : ViewModel() {

    private val _studentState = MutableStateFlow<StudentState>(StudentState.Empty)
    val studentState: StateFlow<StudentState> = _studentState

    private val _selectedStudent = MutableStateFlow<Student?>(null)
    val selectedStudent: StateFlow<Student?> = _selectedStudent

    private val _studentList = MutableStateFlow<List<Student>>(emptyList())
    val studentList: StateFlow<List<Student>> = _studentList

    fun selectStudentById(studentId: Long) {
        viewModelScope.launch {
            repository.findStudentById(studentId)
                .onSuccess { user ->
                    _selectedStudent.value = user
                }
                .onFailure { exception ->
                    Log.e("StudentViewModel", "Error fetching user by ID: ${exception.message}")
                }
        }
    }

    fun findAllStudent() {
        viewModelScope.launch {
            repository.findAllStudents()
                .onSuccess { student ->
                    Log.d("StudentViewModel", "Total Student: ${student.size}")
                    _studentList.value = student
                }
                .onFailure { exception ->
                    Log.e("StudentViewModel", "Failed to fetch users: ${exception.message}")
                }
        }
    }


    fun loadStudentByUserId(userId: Long) {
        viewModelScope.launch {
            repository.findStudentByIdUser(userId)
                .onSuccess { student ->
                    _selectedStudent.value = student
                    _studentState.value = StudentState.Success(student)
                }
                .onFailure { exception ->
                    _studentState.value = StudentState.Error("Estudiante no encontrado: ${exception.message}")
                    Log.e("StudentViewModel", "Error fetching student by user ID: ${exception.message}")
                }
        }
    }

//    fun validateUserCredentials(correo: String, password: String, onResult: (Boolean) -> Unit) {
//        viewModelScope.launch {
//            _userState.value = UsersState.Loading
//
//            repository.findUserByEmail(correo)
//                .onSuccess { user ->
//                    val isValid = user.password == password
//                    if (isValid) {
//                        _userState.value = UsersState.Success(user)
//                        _selectedUser.value = user
//                        onResult(true)
//                    } else {
//                        _userState.value = UsersState.Error("Contraseña incorrecta")
//                        onResult(false)
//                    }
//                }
//                .onFailure { exception ->
//                    _userState.value = UsersState.Error("Usuario no encontrado: ${exception.message}")
//                    onResult(false)
//                }
//        }
//    }


}
