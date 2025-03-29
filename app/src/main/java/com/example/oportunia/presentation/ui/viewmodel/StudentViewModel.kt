package com.example.oportunia.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.domain.model.Cv
import com.example.oportunia.domain.model.Student
import com.example.oportunia.domain.model.UniversityOption
import com.example.oportunia.domain.repository.CvRepository
import com.example.oportunia.domain.repository.StudentRepository
import com.example.oportunia.domain.repository.UniversityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

sealed class StudentState {
    data object Loading : StudentState()
    data class Success(val student: Student) : StudentState()
    data object Empty : StudentState()
    data class Error(val message: String) : StudentState()
}

class StudentViewModel(
    private val repository: StudentRepository,
    private val universityRepository: UniversityRepository,
    private val cvRepository: CvRepository
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

// datos para guardar el estudiante
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre

    private val _apellido1 = MutableStateFlow("")
    val apellido1: StateFlow<String> = _apellido1

    private val _apellido2 = MutableStateFlow("")
    val apellido2: StateFlow<String> = _apellido2


    private val _correo = MutableStateFlow("")
    val correo: StateFlow<String> = _correo


    private val _contrasenna = MutableStateFlow("")
    val contrasenna: StateFlow<String> = _contrasenna


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
                    _studentState.value = StudentState.Error("Error al obtener estudiante: ${exception.message}")
                    Log.e("StudentViewModel", "Error al buscar estudiante por userId: $userId", exception)
                }
        }
    }

    fun getAllUniversitiesNames() {
        viewModelScope.launch {
            universityRepository.findAllUniversities()
                .onSuccess { universities ->
                    _universityNames.value = universities.mapNotNull { it.universityName }
                }
                .onFailure { exception ->
                    Log.e("StudentViewModel", "Error al cargar nombres de universidades", exception)
                }
        }
    }

    fun loadUniversityOptions() {
        viewModelScope.launch {
            universityRepository.findAllUniversities()
                .onSuccess { universities ->
                    _universityOptions.value = universities.map {
                        UniversityOption(it.idUniversity, it.universityName)
                    }
                }
        }
    }




    fun saveStudent(userId: Int) {
        viewModelScope.launch {
            val studentListResult = repository.findAllStudents()
            val nextStudentId = studentListResult.getOrNull()?.size?.plus(1) ?: 1

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








    fun setNombre(valor: String) {
        _nombre.value = valor
    }

    fun setApellido1(valor: String) {
        _apellido1.value = valor
    }

    fun setApellido2(valor: String) {
        _apellido2.value = valor
    }


    fun setIdUniversidad(id: Int) {
        _idUniversidadSeleccionada.value = id
    }

    fun setCorreo(valor: String) {
        _correo.value = valor
    }

    fun setContrasenna(valor: String) {
        _contrasenna.value = valor
    }

    fun updateStudent(studentId: Int, userId: Int) {
        viewModelScope.launch {
            val updatedStudent = Student(
                idStudent = studentId,
                idUser = userId,
                name = _nombre.value,
                lastName1 = _apellido1.value,
                lastName2 = _apellido2.value,
                creationDate = LocalDate.now(), // O mantener la fecha original si está disponible
                universityId = _idUniversidadSeleccionada.value
            )
            repository.updateStudent(updatedStudent)
                .onSuccess {
                    _studentState.value = StudentState.Success(updatedStudent)
                    _selectedStudent.value = updatedStudent
                    Log.d("StudentViewModel", "Estudiante actualizado: $updatedStudent")
                }
                .onFailure { exception ->
                    _studentState.value = StudentState.Error("Error al actualizar estudiante: ${exception.message}")
                    Log.e("StudentViewModel", "Error al actualizar estudiante", exception)
                }
        }
    }




    fun clearStudentData() {
        _nombre.value = ""
        _apellido1.value = ""
        _apellido2.value = ""
        _correo.value = ""
        _contrasenna.value = ""
        _idUniversidadSeleccionada.value = null
    }


    fun insertCv(filePath: String) {
        viewModelScope.launch {
            val student = _selectedStudent.value

            if (student == null) {
                Log.e("StudentViewModel", "No se puede insertar CV: ningún estudiante ha sido seleccionado.")
                return@launch
            }

            val cvId = (0..100000).random()

            val cv = Cv(
                id = cvId,
                name = filePath.substringAfterLast('/'),
                file = filePath,
                studentId = student.idStudent,
                status = false
            )

            Log.d("StudentViewModel", "Estudiante seleccionado: $student")
            Log.d("StudentViewModel", "Insertando CV: $cv")

            cvRepository.insertCv(cv)
        }
    }

    fun printStudentAndCvs() {
        viewModelScope.launch {
            val student = _selectedStudent.value

            if (student == null) {
                Log.e("StudentViewModel", "No hay estudiante seleccionado.")
                return@launch
            }

            Log.d("StudentViewModel", "Estudiante: $student")

            val result = cvRepository.findAllCvByStudentId(student.idStudent)

            result.onSuccess { cvs ->
                if (cvs.isEmpty()) {
                    Log.d("StudentViewModel", "El estudiante no tiene CVs registrados.")
                } else {
                    cvs.forEachIndexed { index, cv ->
                        Log.d("StudentViewModel", "CV #${index + 1}: $cv")
                    }
                }
            }.onFailure { exception ->
                Log.e("StudentViewModel", "Error al obtener CVs del estudiante: ${exception.message}", exception)
            }
        }
    }

    fun loadCvsBySelectedStudent() {
        viewModelScope.launch {
            val student = _selectedStudent.value

            if (student == null) {
                Log.e("StudentViewModel", "No se puede cargar CVs: ningún estudiante ha sido seleccionado.")
                return@launch
            }

            cvRepository.findAllCvByStudentId(student.idStudent)
                .onSuccess { cvs ->
                    _cvList.value = cvs
                    Log.d("StudentViewModel", "CVs cargados: ${cvs.size} para estudiante ${student.idStudent}")
                }
                .onFailure { exception ->
                    Log.e("StudentViewModel", "Error al cargar CVs: ${exception.message}", exception)
                }
        }
    }

    fun deleteCv(cv: Cv) {
        viewModelScope.launch {
            cvRepository.deleteCv(cv)
                .onSuccess {
                    _cvList.value = _cvList.value.filterNot { it.id == cv.id }
                    Log.d("StudentViewModel", "CV eliminado: $cv")
                }
                .onFailure { exception ->
                    Log.e("StudentViewModel", "Error al eliminar CV: ${exception.message}", exception)
                }
        }
    }

    fun setCvAsActive(cvId: Int) {
        viewModelScope.launch {
            val student = _selectedStudent.value

            if (student == null) {
                Log.e("StudentViewModel", "No se puede cambiar el estado: ningún estudiante ha sido seleccionado.")
                return@launch
            }

            val currentList = _cvList.value

            val updatedList = currentList.map { cv ->
                if (cv.id == cvId) {
                    val updated = cv.copy(status = true)
                    cvRepository.updateCv(updated)
                    updated
                } else {
                    if (cv.status) {
                        val updated = cv.copy(status = false)
                        cvRepository.updateCv(updated)
                        updated
                    } else {
                        cv
                    }
                }
            }

            _cvList.value = updatedList
            Log.d("StudentViewModel", "Estado actualizado: solo el CV con id $cvId está activo.")
        }
    }









}
