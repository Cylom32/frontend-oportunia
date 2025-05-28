package com.example.oportunia.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.data.remote.dto.StudentWihtoutIdDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

import com.example.oportunia.domain.model.Cv
import com.example.oportunia.domain.model.Student
import com.example.oportunia.domain.model.UniversityOption
import com.example.oportunia.domain.repository.CvRepository
import com.example.oportunia.domain.repository.StudentRepository
import com.example.oportunia.domain.repository.UniversityRepository

sealed class StudentState {
    object Loading : StudentState()
    data class Success(val student: Student) : StudentState()
    object Empty : StudentState()
    data class Error(val message: String) : StudentState()
}

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val repository: StudentRepository,
    private val universityRepository: UniversityRepository,
   // private val cvRepository: CvRepository
) : ViewModel() {

    private val _cvList = MutableStateFlow<List<Cv>>(emptyList())
    val cvList: StateFlow<List<Cv>> = _cvList

    private val _studentState = MutableStateFlow<StudentState>(StudentState.Empty)
    val studentState: StateFlow<StudentState> = _studentState

    private val _selectedStudent = MutableStateFlow<Student?>(null)
    val selectedStudent: StateFlow<Student?> = _selectedStudent

    private val _studentList = MutableStateFlow<List<Student>>(emptyList())
    val studentList: StateFlow<List<Student>> = _studentList

    private val _universityNames = MutableStateFlow<List<String>>(emptyList())
    val universityNames: StateFlow<List<String>> = _universityNames

    private val _universityOptions = MutableStateFlow<List<UniversityOption>>(emptyList())
    val universityOptions: StateFlow<List<UniversityOption>> = _universityOptions

    // Campos para crear/actualizar estudiante
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre

    private val _apellido1 = MutableStateFlow("")
    val apellido1: StateFlow<String> = _apellido1

    private val _apellido2 = MutableStateFlow("")
    val apellido2: StateFlow<String> = _apellido2

    private val _idUniversidadSeleccionada = MutableStateFlow<Int?>(null)
    val idUniversidadSeleccionada: StateFlow<Int?> = _idUniversidadSeleccionada

    fun loadStudentByUserId(userId: Int) {
        viewModelScope.launch {
            _studentState.value = StudentState.Loading
            repository.findStudentByIdUser(userId)
                .onSuccess { student ->
                    _studentState.value = StudentState.Success(student)
                    _selectedStudent.value = student
                }
                .onFailure { exception ->
                    _studentState.value =
                        StudentState.Error("Error al obtener estudiante: ${exception.message}")
                    Log.e(
                        "StudentViewModel",
                        "Error al buscar estudiante por userId: $userId",
                        exception
                    )
                }
        }
    }

    fun saveStudent(userId: Int) {
        viewModelScope.launch {
            val studentListResult = repository.findAllStudents()
            val nextStudentId = (studentListResult.getOrNull()?.size ?: 0) + 1
            val student = Student(
                idStudent = nextStudentId,
                idUser = userId,
                name = _nombre.value,
                lastName1 = _apellido1.value,
                lastName2 = _apellido2.value,
                creationDate = LocalDate.now(),
                universityId = _idUniversidadSeleccionada.value
            )
            repository.insertStudent(student)
            Log.d("StudentViewModel", "Estudiante guardado: $student")
        }
    }

    fun updateStudent(studentId: Int, userId: Int) {
        viewModelScope.launch {
            val updatedStudent = Student(
                idStudent = studentId,
                idUser = userId,
                name = _nombre.value,
                lastName1 = _apellido1.value,
                lastName2 = _apellido2.value,
                creationDate = LocalDate.now(),
                universityId = _idUniversidadSeleccionada.value
            )
            repository.updateStudent(updatedStudent)
                .onSuccess {
                    _studentState.value = StudentState.Success(updatedStudent)
                    _selectedStudent.value = updatedStudent
                    Log.d("StudentViewModel", "Estudiante actualizado: $updatedStudent")
                }
                .onFailure { exception ->
                    _studentState.value =
                        StudentState.Error("Error al actualizar estudiante: ${exception.message}")
                    Log.e("StudentViewModel", "Error al actualizar estudiante", exception)
                }
        }
    }

    fun clearStudentData() {
        _nombre.value = ""
        _apellido1.value = ""
        _apellido2.value = ""
        _idUniversidadSeleccionada.value = null
    }


    fun createStudentWithoutId(
        userId: Int,
        rawFirstName: String,
        rawLastName1: String,
        rawLastName2: String,
        universityId: Int
    ) {
        viewModelScope.launch {
            // 1) Construye el DTO con los campos que espera el backend
            val studentDto = StudentWihtoutIdDTO(
                firstName    = rawFirstName,
                lastName1    = rawLastName1,
                lastName2    = rawLastName2,
                idUser       = userId,
                idUniversity = universityId
            )

            // 2) Llama al repositorio
            val result: Result<Student> = repository.saveStudentNoId(studentDto)

            // 3) Manejo de respuesta
            result
                .onSuccess { created ->
                    Log.d("StudentVM", "✅ Estudiante creado: $created")
                }
                .onFailure { error ->
                    Log.e("StudentVM", "❌ Error al crear estudiante", error)
                }
        }
    }



}














