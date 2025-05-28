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


    fun createStudentWithoutId(
        userId: Int,
        rawFirstName: String,
        rawLastName1: String,
        rawLastName2: String,
        universityId: Int
    ) {
        viewModelScope.launch {

            val studentDto = StudentWihtoutIdDTO(
                firstName    = rawFirstName,
                lastName1    = rawLastName1,
                lastName2    = rawLastName2,
                idUser       = userId,
                idUniversity = universityId
            )


            val result: Result<Student> = repository.saveStudentNoId(studentDto)


            result
                .onSuccess { created ->
                   // Log.d("StudentVM", "✅ Estudiante creado: $created")
                }
                .onFailure { error ->
                    //Log.e("StudentVM", "❌ Error al crear estudiante", error)
                }
        }
    }







    /** Obtiene y expone el Student del userId dado */
    fun fetchStudentByUserId(token: String, userId: Int) {
        viewModelScope.launch {
            _studentState.value = StudentState.Loading
            repository.findStudentByUserId(token, userId)
                .onSuccess { student ->
                    _selectedStudent.value = student
                    _studentState.value = StudentState.Success(student)
                    Log.d("StudentVM", "✅ Estudiante obtenido: $student")
                }
                .onFailure { ex ->
                    _studentState.value = StudentState.Error(ex.message ?: "Error al obtener estudiante")
                    Log.e("StudentVM", "❌ Error al obtener estudiante userId=$userId", ex)
                }
        }
    }



}














