package com.example.oportunia.presentation.ui.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.data.remote.dto.CVListResponse
import com.example.oportunia.data.remote.dto.StudentWihtoutIdDTO
import com.example.oportunia.domain.model.CVInput
import com.example.oportunia.domain.model.CVResponseS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.AndroidViewModel

import com.example.oportunia.domain.model.Cv
import com.example.oportunia.domain.model.CvResponse
import com.example.oportunia.domain.model.MessageResponseS
import com.example.oportunia.domain.model.Student
import com.example.oportunia.domain.model.StudentUpdateInput
import com.example.oportunia.domain.model.UniversityOption
import com.example.oportunia.domain.repository.CompanyRepository
import com.example.oportunia.domain.repository.CvRepository
import com.example.oportunia.domain.repository.StudentRepository
import com.example.oportunia.domain.repository.UniversityRepository
import dagger.hilt.android.internal.Contexts.getApplication
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

sealed class StudentState {
    object Loading : StudentState()
    data class Success(val student: Student) : StudentState()
    object Empty : StudentState()
    data class Error(val message: String) : StudentState()
}

@HiltViewModel
class StudentViewModel @Inject constructor(
    application: Application,
    private val repository: StudentRepository,
    private val repositoryCM: CompanyRepository,
    private val universityRepository: UniversityRepository,
   // private val cvRepository: CvRepository
) : AndroidViewModel(application)  {

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



    private var studentId: Int? = null

    private val _studentIdd = MutableStateFlow<Int?>(null)
    val studentIdd: StateFlow<Int?> = _studentIdd



    /** Obtiene y expone el Student del userId dado */
    // StudentViewModel.kt

// 1) Agrega un callback onResult al método
    fun fetchStudentByUserId(
        token: String,
        userId: Int,
        onResult: (Boolean) -> Unit
    ) {
        Log.d("StudentVM", "▶️ Iniciando fetchStudentByUserId con token=$token, userId=$userId")
        viewModelScope.launch {
            _studentState.value = StudentState.Loading
            Log.d("StudentVM", "⏳ Estado: Loading")
            repository.findStudentByUserId(token, userId)
                .onSuccess { student ->
                    Log.d("StudentVM", "✅ Respuesta recibida: $student")
                    studentId = student.idStudent
                    Log.d("StudentVM", "→ studentId privado asignado: $studentId")
                    _studentIdd.value = student.idStudent
                    Log.d("StudentVM", "→ _studentIdd emitido: ${_studentIdd.value}")
                    _selectedStudent.value = student
                    _studentState.value = StudentState.Success(student)
                    Log.d("StudentVM", "🎉 Estado: Success con student=$student (idStudent=${student.idStudent})")
                    onResult(true) // sí encontró al estudiante
                }
                .onFailure { ex ->
                    _studentState.value = StudentState.Error(ex.message ?: "Error al obtener estudiante")
                    Log.e("StudentVM", "❌ Error al obtener estudiante userId=$userId → ${ex.message}", ex)
                    onResult(false) // no encontró al estudiante
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





////////////---------------------      para obtener la lista de mensajes enviados del estudiante ///////////////////////////////////////////////////////////////////


    private val _messagesByStudent = MutableStateFlow<List<MessageResponseS>?>(null)
    val messagesByStudent: StateFlow<List<MessageResponseS>?> = _messagesByStudent

    fun fetchMessagesByStudent(token: String, studentId: Int) = viewModelScope.launch {
        repositoryCM.findMessagesByStudent(token, studentId)
            .onSuccess { list ->
                _messagesByStudent.value = list
                Log.d("CompanyVM", "Messages for student $studentId → $list")
            }
            .onFailure { ex ->
                Log.e("CompanyVM", "Error fetching messages for student $studentId", ex)
            }
    }



    ////////////---------------------      para obtener la lista de mensajes enviados del estudiante ///////////////////////////////////////////////////////////////////



    ////////////---------------------      para obtener la lista de cv del estudiante ///////////////////////////////////////////////////////////////////



    /////////////  ----- PARA CARGAR LA LISTA DE CV DEL ESTUDIANTE  ----- /////////////


    private val _cvlistaa = MutableStateFlow<List<CVResponseS>>(emptyList())
    val cvlistaa: StateFlow<List<CVResponseS>> = _cvlistaa

    // 3) Función para invocar al repositorio y poblar _cvlista
    fun fetchCvLista(token: String) {
        val id: Int = studentIdd.value ?: return
        viewModelScope.launch {

            repository.findCvListByStudent(token, id)
                .onSuccess { lista ->
                    _cvlistaa.value = lista

                    // Opcional: loguear cada CV
                    lista.forEach { cv ->
                        Log.d(
                            "StudentVM",
                            "CV → idCv=${cv.idCv}, name='${cv.name}', file='${cv.file}'"
                        )
                    }
                }
                .onFailure { ex ->
                    Log.e("StudentVM", "Error al cargar CVs: ${ex.message}")
                }
        }
    }




    /////////////  ----- PARA CARGAR LA LISTA DE CV DEL ESTUDIANTE  ----- /////////////

    ////////////---------------------      para obtener la lista de cv del estudiante ///////////////////////////////////////////////////////////////////







    ////////////---------------------      PARA ELIMINAR EL CV SELECCIONADO ///////////////////////////////////////////////////////////////////

    private val _deleteResult = MutableStateFlow<Boolean?>(null)
    val deleteResult: StateFlow<Boolean?> = _deleteResult

    fun deleteCv(token: String, cvId: Int) = viewModelScope.launch {
        repository.deleteCvById(token, cvId)
            .onSuccess {
                _deleteResult.value = true
            }
            .onFailure {
                _deleteResult.value = false
                Log.e("StudentViewModel", "Error al eliminar CV id=$cvId", it)
            }
    }

    fun resetDeleteResult() {
        _deleteResult.value = null
    }



    ////////////---------------------      PARA ELIMINAR EL CV SELECCIONADO ///////////////////////////////////////////////////////////////////





    ////////////---------------------      PARA agregar CV al estudiante ///////////////////////////////////////////////////////////////////


    // Dentro de StudentViewModel.kt

//    // 1) Añade esta propiedad para exponer el resultado de la carga
//    private val _createCvResult = MutableStateFlow<Boolean?>(null)
//    val createCvResult: StateFlow<Boolean?> = _createCvResult
//
//    // 2) Método para invocar al repositorio y subir el CV
//    fun createCv(token: String, name: String, file: String, studentId: Int) {
//        val cvInput = CVInput(name = name, file = file, idStudent = studentId)
//        viewModelScope.launch {
//            repository.createCv(token, cvInput)
//                .onSuccess {
//                    _createCvResult.value = true
//                    Log.d("StudentVM", "CV enviado correctamente: $cvInput")
//                }
//                .onFailure { ex ->
//                    _createCvResult.value = false
//                    Log.e("StudentVM", "Error al crear CV: ${ex.message}")
//                }
//        }
//    }
//


    private val _createCvResult = MutableStateFlow<Boolean?>(null)
    val createCvResult: StateFlow<Boolean?> = _createCvResult

    // 2) Método para invocar al repositorio y subir el CV, recargando la lista en onSuccess
    fun createCv(token: String, name: String, file: String, studentId: Int) {
        val cvInput = CVInput(name = name, file = file, idStudent = studentId)
        viewModelScope.launch {
            repository.createCv(token, cvInput)
                .onSuccess {
                    _createCvResult.value = true
                    Log.d("StudentVM", "CV enviado correctamente: $cvInput")
                    // Apenas se crea el CV, recargar la lista:
                    fetchCvLista(token)
                }
                .onFailure { ex ->
                    _createCvResult.value = false
                    Log.e("StudentVM", "Error al crear CV: ${ex.message}")
                }
        }
    }





    ////////////---------------------      PARA agregar CV al estudiante ///////////////////////////////////////////////////////////////////


    ////////////---------------------      PARA actualizar la info del studiante ///////////////////////////////////////////////////////////////////




    fun updateStudent(
        token: String,
        studentId: Int,
        rawFirstName: String,
        rawLastName1: String,
        rawLastName2: String,
        universityId: Int,
        userId: Int
    ) {
        viewModelScope.launch {
            val updateDto = StudentUpdateInput(
                firstName    = rawFirstName,
                lastName1    = rawLastName1,
                lastName2    = rawLastName2,
                idUniversity = universityId,
                idUser       = userId
            )

            repository.updateStudent(token, studentId, updateDto)
                .onSuccess {
                    val currentStudent = (_studentState.value as? StudentState.Success)?.student
                    if (currentStudent != null) {
                        val updatedStudent = currentStudent.copy(
                            name         = rawFirstName,
                            lastName1    = rawLastName1,
                            lastName2    = rawLastName2,
                            universityId = universityId
                        )
                        _studentState.value = StudentState.Success(updatedStudent)
                    }
                }
                .onFailure { ex ->
                    _studentState.value = StudentState.Error(ex.message ?: "Error desconocido")
                }
        }
    }






    ////////////---------------------      PARA actualizar la info del studiante ///////////////////////////////////////////////////////////////////


    ////////////---------------------      ANALIZAR EL CV CON LA IA ///////////////////////////////////////////////////////////////////




    // 1) Estados posibles de la carga/análisis de CV
    sealed class CvUploadState {
        object Idle : CvUploadState()
        object Loading : CvUploadState()
        data class Success(val response: CvResponse) : CvUploadState()
        data class Error(val message: String) : CvUploadState()
    }

    // 2) Exponer un StateFlow para la UI
    private val _uploadState = MutableStateFlow<CvUploadState>(CvUploadState.Idle)
    val uploadState: StateFlow<CvUploadState> = _uploadState

    /**
     * 3) Envía el PDF al servidor (con name/idStudent “quemados”) y emite estados.
     *    Además, limpia el texto de “analysis” para eliminar '#' '*' '-' al inicio de línea.
     */
    fun sendCv(fileUri: Uri, fileName: String, studentId: Int) = viewModelScope.launch {
        _uploadState.value = CvUploadState.Loading
        Log.d("CvUpload", "Estado → Loading")

        try {
            // 3.1) Abrir InputStream desde el URI usando el ContentResolver
            val resolver = getApplication<Application>().contentResolver
            val inputStream = resolver.openInputStream(fileUri)
                ?: throw Exception("No se pudo abrir InputStream para URI: $fileUri")

            // 3.2) Crear un archivo temporal en el cacheDir de la app
            val tempFile = File(getApplication<Application>().cacheDir, fileName)
            tempFile.outputStream().use { output ->
                inputStream.copyTo(output)
            }

            // 3.3) Construir MultipartBody.Part a partir del archivo temporal
            val requestFile = tempFile
                .asRequestBody("application/pdf".toMediaTypeOrNull())
            val filePart =
                MultipartBody.Part.createFormData("file", tempFile.name, requestFile)

            // 3.4) Partes “quemadas” para name e idStudent
            val namePart = "JSJ".toRequestBody("text/plain".toMediaTypeOrNull())
            val idStudentPart = "-1".toRequestBody("text/plain".toMediaTypeOrNull())

            // 3.5) Llamar al repositorio para subir y obtener respuesta
            val result = repository.uploadRemoteCv(filePart, namePart, idStudentPart)
            if (result.isSuccess) {
                // Obtener CvResponse original
                val rawResponse = result.getOrNull()!!

                // 3.6) Limpiar el campo “analysis” línea por línea:
                val cleanedAnalysis = rawResponse.analysis
                    .lines()
                    .map { line ->
                        line
                            .trimStart()
                            .removePrefix("#")
                            .removePrefix("*")
                            .removePrefix("-")
                            .trimStart()
                    }
                    .joinToString("\n")

                // 3.7) Construir un nuevo CvResponse con “analysis” limpio
                val cleanedResponse = rawResponse.copy(analysis = cleanedAnalysis)

                _uploadState.value = CvUploadState.Success(cleanedResponse)
                Log.d(
                    "CvUpload",
                    "Estado → Success, idStudent=${cleanedResponse.idStudent}, name=${cleanedResponse.name}"
                )
            } else {
                val errMsg = result.exceptionOrNull()?.message ?: "Error desconocido"
                _uploadState.value = CvUploadState.Error(errMsg)
                Log.e("CvUpload", "Estado → Error: $errMsg")
            }

            // 3.8) Eliminar el archivo temporal del cacheDir
            tempFile.delete()
        } catch (e: Exception) {
            val msg = e.message ?: "Error inesperado"
            _uploadState.value = CvUploadState.Error(msg)
            Log.e("CvUpload", "Estado → Error: $msg")
        }
    }


    ////////////---------------------      ANALIZAR EL CV CON LA IA ///////////////////////////////////////////////////////////////////






}














