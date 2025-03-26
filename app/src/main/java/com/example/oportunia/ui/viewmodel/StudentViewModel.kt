package com.example.oportunia.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.domain.model.Student
import com.example.oportunia.domain.repository.StudentRepository
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




    fun loadStudentByUserId(userId: Int) {
        viewModelScope.launch {
            _studentState.value = StudentState.Loading
            repository.findStudentByIdUser(userId)
                .onSuccess { student ->
                    _studentState.value = StudentState.Success(student)
                    _selectedStudent.value = student
                }
                .onFailure { exception ->
                    _studentState.value = StudentState.Error("Error al obtener estudiante: ${exception.message}")
                    Log.e("StudentViewModel", "Error al buscar estudiante por userId: $userId", exception)
                }
        }
    }





}
