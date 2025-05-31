package com.example.oportunia.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.data.remote.dto.CVListResponse
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
import com.example.oportunia.domain.repository.CompanyRepository
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
    private val repositoryCM: CompanyRepository,
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



////////// --------------------------------------        PARA OBTENER EL ID DEL ESTUDIANTE        -------------------------------------- //////////

// ocupo aqúi el id para tener la lista de cv...

    private var studentId: Int? = null

    private val _studentIdd = MutableStateFlow<Int?>(null)
    val studentIdd: StateFlow<Int?> = _studentIdd



    /** Obtiene y expone el Student del userId dado */
    fun fetchStudentByUserId(token: String, userId: Int) {
        viewModelScope.launch {
            _studentState.value = StudentState.Loading
            repository.findStudentByUserId(token, userId)
                .onSuccess { student ->
                    // 3) Guardar el id en la variable privada
                    studentId = student.idStudent

                    // 4) Empujar el mismo valor al StateFlow
                    _studentIdd.value = student.idStudent

                    // Exponer el student completo
                    _selectedStudent.value = student
                    _studentState.value = StudentState.Success(student)

                    Log.d(
                        "StudentVM",
                        "✅ Estudiante obtenido: $student (idStudent=$studentId)"
                    )
                }
                .onFailure { ex ->
                    _studentState.value = StudentState.Error(ex.message ?: "Error al obtener estudiante")
                    Log.e("StudentVM", "❌ Error al obtener estudiante userId=$userId", ex)
                }
        }
    }

    ////////// --------------------------------------        PARA OBTENER EL ID DEL ESTUDIANTE        -------------------------------------- //////////


    ////////// --------------------------------------        PARA OBTENER EL la lista de cv del estudiantes por toke y id       -------------------------------------- //////////


    // Dentro de tu ViewModel
    private val _cvlista = MutableStateFlow<List<CVListResponse>>(emptyList())
    val cvlista: StateFlow<List<CVListResponse>> = _cvlista

    fun fetchCvList(token: String, studentId: Int) {
        viewModelScope.launch {
            repositoryCM.findCvListByStudent(token, studentId)
                .onSuccess { lista ->
                    _cvlista.value = lista

                    lista.forEach { cv ->
                        Log.d(
                            "CvViewModel",
                            "CV → idCv=${cv.idCv}, name='${cv.name}', file='${cv.file}'"
                        )
                    }
                }
                .onFailure { ex ->
                    Log.e("CvViewModel", "Error al cargar CVs: ${ex.message}")
                }
        }
    }




    ////////// --------------------------------------        PARA OBTENER EL la lista de cv del estudiantes por toke y id       -------------------------------------- //////////



}














