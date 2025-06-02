package com.example.oportunia.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.data.remote.dto.InboxInput
import com.example.oportunia.data.remote.dto.InboxResult
import com.example.oportunia.data.remote.dto.PublicationByCompanyDTO
import com.example.oportunia.data.remote.dto.PublicationDetailDTO
import com.example.oportunia.data.remote.dto.PublicationFilterDTO
import com.example.oportunia.domain.model.CompanyInputCM
import com.example.oportunia.domain.model.CompanyPublicationInput
import com.example.oportunia.domain.model.CompanyWithNetworks
import com.example.oportunia.domain.model.MessageInput
import com.example.oportunia.domain.model.MessageResponseC
import com.example.oportunia.domain.model.SocialNetwork
import com.example.oportunia.domain.model.SocialNetworkInputRS
import com.example.oportunia.domain.model.SocialNetworkResponseRS
import com.example.oportunia.domain.model.UserImgInputCM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.oportunia.domain.repository.CompanyRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val repository: CompanyRepository
) : ViewModel() {




    private val _publications = MutableStateFlow<List<PublicationFilterDTO>>(emptyList())
    val publications: StateFlow<List<PublicationFilterDTO>> = _publications

    private val _pubError = MutableStateFlow<String?>(null)
    val pubError: StateFlow<String?> = _pubError


    // CompanyViewModel.kt
    fun fetchPublications(
        areaId: Int? = null,
        locationId: Int? = null,
        paid: Boolean? = null
    ) = viewModelScope.launch {
        repository.findPublicationsByFilter(areaId, locationId, paid)
            .onSuccess { list ->
                Log.d("CompanyViewModel", "Publicaciones recibidas → $list")
                _publications.value = list
                _pubError.value = null
            }
            .onFailure { ex ->
                Log.e("CompanyViewModel", "Error al obtener publicaciones", ex)
                _pubError.value = ex.message
            }
    }


///////////////////////////////  -------------  PARA
// GUARDAR EL ID DE LA COMPAÑIA SELECCIONADA EN EL SCROLL   --------  ///////////////////////



    private val _selectedCompanyId = MutableStateFlow<Int?>(null)
    val selectedCompanyId: StateFlow<Int?> = _selectedCompanyId


    // id de la compañiaaaaaa

    fun setSelectedCompanyId(id: Int) {
        _selectedCompanyId.value = id
    }




///////////////////////////////  -------------  PARA GUARDAR EL ID DE LA COMPAÑIA SELECCIONADA EN EL SCROLL   --------  ///////////////////////



///////////////////////////////  -------------  info de la compañia y sus social networks   --------  ///////////////////////


    private val _companyWithNetworks = MutableStateFlow<CompanyWithNetworks?>(null)
    val companyWithNetworks: StateFlow<CompanyWithNetworks?> = _companyWithNetworks

    private val _companyName = MutableStateFlow<String?>(null)
    val companyName: StateFlow<String?> = _companyName

    fun fetchCompanyWithNetworks(idCompany: Int) = viewModelScope.launch {
        repository.findCompanyWithNetworks(idCompany)
            .onSuccess { dto ->
                _companyWithNetworks.value = dto

                // guarda el nombre en la variable
                _companyName.value = dto.companyName

                Log.d("CompanyVM", "CompanyWithNetworks → " +
                        "id=${dto.idCompany}, " +
                        "name='${dto.companyName}', " +
                        "description='${dto.companyDescription}'"
                )
                dto.socialNetworks.forEach { sn ->
                    Log.d("CompanyVM", "  • SocialNetwork → " +
                            "id=${sn.idSocialNetwork}, " +
                            "link='${sn.link}'"
                    )
                }
            }
            .onFailure { ex ->
                Log.e("CompanyVM", "Error cargando redes sociales", ex)
            }
    }


///////////////////////////////  -------------  info de la compañia y sus social networks   --------  ///////////////////////


///////////////////////////////  -------------  logo de la compañia   --------  ///////////////////////

    private val _companyLogo = MutableStateFlow<String?>(null)
    val companyLogo: StateFlow<String?> = _companyLogo


    fun setCompanyLogo(url: String) {
        _companyLogo.value = url
    }


///////////////////////////////  -------------  logo de la compañia   --------  ///////////////////////


///////////////////////////////  -------------  para obtener las publicaciones de la compañia segund id   --------  ///////////////////////


    private val _companyPublications = MutableStateFlow<List<PublicationByCompanyDTO>>(emptyList())
    val companyPublications: StateFlow<List<PublicationByCompanyDTO>> = _companyPublications

    private val _publicationsError = MutableStateFlow<String?>(null)
    val publicationsError: StateFlow<String?> = _publicationsError

    /**
     * @param authToken debe incluir el prefijo "Bearer "
     * @param companyIdParam ID de la compañía
     */



    fun fetchPublicationsByCompany(
        authToken: String,
        companyIdParam: Int
    ) = viewModelScope.launch {
        repository
            .findPublicationsByCompany(authToken, companyIdParam)
            .onSuccess { publications ->
                _companyPublications.value = publications
                _publicationsError.value = null

                // Imprimir cada publicación en el log
                publications.forEach { pub ->
                    Log.d("CompanyVM", "Publication fetched: $pub")
                }

                // Imprimir el arreglo completo en JSON
                val json = Gson().toJson(publications)
                Log.d("CompanyVM", "Publications JSON: $json")
            }
            .onFailure { ex ->
                _publicationsError.value = ex.message
                Log.e("CompanyVM", "Error fetching publications: ${ex.message}")
            }
    }



///////////////////////////////  -------------  para obtener las publicaciones de la compañia segund id   --------  ///////////////////////


///////////////////////////////  -------------  para obtener la publicacion segund id   --------  ///////////////////////


    private val _publicationDetail = MutableStateFlow<PublicationDetailDTO?>(null)
    val publicationDetail: StateFlow<PublicationDetailDTO?> = _publicationDetail

    private val _publicationError = MutableStateFlow<String?>(null)
    val publicationError: StateFlow<String?> = _publicationError



    fun fetchPublicationById(id: Int) = viewModelScope.launch {
        repository.findPublicationById(id)
            .onSuccess { detail ->
                _publicationDetail.value = detail
                _publicationError.value = null
                Log.d("CompanyViewModel", "Detalle publicación → $detail")
            }
            .onFailure { ex ->
                _publicationError.value = ex.message
                Log.e("CompanyViewModel", "Error al obtener publicación id=$id", ex)
            }
    }



    ///******************


    private val _selectedPublicationId = MutableStateFlow<Int?>(null)
    val selectedPublicationId: StateFlow<Int?> = _selectedPublicationId


    fun selectPublication(id: Int) {
        _selectedPublicationId.value = id
    }


    ///******************




///////////////////////////////  -------------  para obtener la publicacion segund id   --------  ///////////////////////


///////////////////////////////  -------------  para obtener la EL IBOX SEGUN ID COMPANY   --------  ///////////////////////

    private val _inboxByCompany = MutableStateFlow<InboxResult?>(null)
    val inboxByCompany: StateFlow<InboxResult?> = _inboxByCompany

    fun fetchInboxByCompany(companyId: Int) = viewModelScope.launch {
        repository.findInboxByCompany(companyId)
            .onSuccess {
                _inboxByCompany.value = it
                Log.d("CompanyViewModel", "Inbox for company $companyId: $it")
            }
            .onFailure { ex ->
                Log.e("CompanyViewModel", "Error fetching inbox for company $companyId", ex)
            }
    }

///////////////////////////////  -------------  para obtener la EL IBOX SEGUN ID COMPANY   --------  ///////////////////////




///////////////////////////////  -------------  ENVIARRRRR MENSAJEEEEEEEEEEEEEE   --------  ///////////////////////
private val _sendSuccess = MutableStateFlow<Boolean?>(null)
    val sendSuccess: StateFlow<Boolean?> = _sendSuccess

    fun sendMessage(
        rawToken: String,
        detail: String,
        file: String,
        idInbox: Int,
        idStudent: Int
    ) {
        val input = MessageInput(
            detail = detail,
            file = file,
            idInbox = idInbox,
            idStudent = idStudent
        )

        viewModelScope.launch {
            repository.sendMessage(
                token = rawToken,
                input = input
            ).onSuccess {
                Log.d("CompanyViewModel", "Mensaje enviado correctamente")
                _sendSuccess.value = true
            }.onFailure { ex ->
                Log.e("CompanyViewModel", "Error al enviar mensaje: ${ex.message}", ex)
                _sendSuccess.value = false
            }
        }
    }

    fun clearSendStatus() {
        _sendSuccess.value = null
    }


///////////////////////////////  -------------  ENVIARRRRR MENSAJEEEEEEEEEEEEEE   --------  ///////////////////////



///////////////////////////////  -------------  PARA BUSCAR LOS DATOS DE LA COMPAÑIA POR ID DE USER   --------  ///////////////////////


    private val _userIdC = MutableStateFlow<Int?>(null)
    val userIdC: StateFlow<Int?> = _userIdC

    private val _companyIdC = MutableStateFlow<Int?>(null)
    val companyIdC: StateFlow<Int?> = _companyIdC

    private val _companyNameC = MutableStateFlow<String?>(null)
    val companyNameC: StateFlow<String?> = _companyNameC

    private val _companyDescriptionC = MutableStateFlow<String?>(null)
    val companyDescriptionC: StateFlow<String?> = _companyDescriptionC

    fun fetchCompanyByUserC(userId: Int) = viewModelScope.launch {
        _userIdC.value = userId

        repository.findCompanyByUser(userId)
            .onSuccess { resp ->
                _companyIdC.value = resp.idCompany
                _companyNameC.value = resp.companyName
                _companyDescriptionC.value = resp.companyDescription
                Log.d(
                    "CompanyViewModel",
                    "Compañía obtenida correctamente → userIdC=$userId, companyIdC=${resp.idCompany}, companyNameC=${resp.companyName}, companyDescriptionC=${resp.companyDescription}"
                )
            }
            .onFailure { ex ->
                Log.e("CompanyViewModel", "Error al obtener compañía por usuario: ${ex.message}", ex)
            }
    }





///////////////////////////////  -------------  PARA BUSCAR LOS DATOS DE LA COMPAÑIA POR ID DE USER   --------  ///////////////////////





///////////////////////////////  -------------  OBTENER TODA LA INFO DE LA COMPAÑIA POR ID DE COMPAÑIA   --------  ///////////////////////


    private val _companyWithNetworksk = MutableStateFlow<CompanyWithNetworks?>(null)
    val companyWithNetworksk: StateFlow<CompanyWithNetworks?> = _companyWithNetworksk

    private val _companyNamek = MutableStateFlow<String?>(null)
    val companyNamek: StateFlow<String?> = _companyNamek

    private val _companyDescriptionk = MutableStateFlow<String?>(null)
    val companyDescriptionk: StateFlow<String?> = _companyDescriptionk

    private val _socialNetworksk = MutableStateFlow<List<SocialNetwork>>(emptyList())
    val socialNetworksk: StateFlow<List<SocialNetwork>> = _socialNetworksk

    fun fetchCompanyWithNetworksk(idCompany: Int) = viewModelScope.launch {
        repository.findCompanyWithNetworks(idCompany)
            .onSuccess { dto ->
                _companyWithNetworksk.value = dto
                _companyNamek.value = dto.companyName
                _companyDescriptionk.value = dto.companyDescription
                _socialNetworksk.value = dto.socialNetworks

                Log.d(
                    "CompanyVM",
                    "CompanyWithNetworksk → " +
                            "id=${dto.idCompany}, " +
                            "name='${dto.companyName}', " +
                            "description='${dto.companyDescription}'"
                )
                dto.socialNetworks.forEach { sn ->
                    Log.d(
                        "CompanyVM",
                        "  • SocialNetworkk → " +
                                "id=${sn.idSocialNetwork}, " +
                                "link='${sn.link}'"
                    )
                }
            }
            .onFailure { ex ->
                Log.e("CompanyVM", "Error cargando redes socialesk", ex)
            }
    }


    private val _tokenC = MutableStateFlow<String?>(null)
    val tokenC: StateFlow<String?> = _tokenC

    fun setTokenC(token: String) {
        _tokenC.value = token
    }


///////////////////////////////  -------------  OBTENER TODA LA INFO DE LA COMPAÑIA POR ID DE COMPAÑIA   --------  ///////////////////////



///////////////////////////////  -------------  OBTENER TODAS LAS REDES DE LA COMPAÑIA POR ID DE COMPAÑIA   --------  ///////////////////////

    private val _socialNetworksC = MutableStateFlow<List<SocialNetwork>>(emptyList())
    val socialNetworksC: StateFlow<List<SocialNetwork>> = _socialNetworksC

    fun fetchCompanySocialNetworksC(idCompany: Int) = viewModelScope.launch {
        repository.findCompanyWithNetworks(idCompany)
            .onSuccess { dto ->
                _socialNetworksC.value = dto.socialNetworks
            }
            .onFailure { /* manejar error si es necesario */ }
    }


///////////////////////////////  -------------  OBTENER TODAS LAS REDES DE LA COMPAÑIA POR ID DE COMPAÑIA   --------  ///////////////////////



///////////////////////////////  -------------  para sacar la imagen y email de usuario company --------  ///////////////////////



    private val _emailShow = MutableStateFlow<String?>(null)
    val emailShow: StateFlow<String?> = _emailShow

    private val _imgShow = MutableStateFlow<String?>(null)
    val imgShow: StateFlow<String?> = _imgShow

    fun fetchUserCompanyById() {
        val token = _tokenC.value.orEmpty()
        val userIdCompany = _userIdC.value

        if (!token.isNullOrEmpty() && userIdCompany != null) {
            viewModelScope.launch {
                repository.findUserCompanyById(token, userIdCompany)
                    .onSuccess { userCompany ->
                        _emailShow.value = userCompany.email
                        _imgShow.value = userCompany.img

                        Log.d("ViewModel", "✅ Email guardado: ${_emailShow.value}")
                        Log.d("ViewModel", "✅ Imagen guardada: ${_imgShow.value}")
                    }
                    .onFailure { error ->
                        Log.e("ViewModel", "❌ Error obteniendo datos: ${error.message}")
                    }
            }
        } else {
            Log.e("ViewModel", "❌ Token o ID de usuario no disponibles.")
        }
    }

///////////////////////////////  -------------  para sacar la imagen y email de usuario company --------  ///////////////////////





///////////////////////////////  -------------  PARA ELIMINAR UNA PUBLICACION --------  ///////////////////////

    fun deletePublicationById(publicationId: Int) = viewModelScope.launch {
        val token = _tokenC.value.orEmpty()
        val companyId = _companyIdC.value

        Log.d("CompanyVM", "Token usado: $token")
        Log.d("CompanyVM", "El ID de publicación a eliminar es: $publicationId")

        if (token.isNotEmpty() && companyId != null) {
            repository.deletePublicationById(token, publicationId)
                .onSuccess {
                    Log.d("CompanyVM", "✅ Publicación eliminada con éxito")
                    fetchPublicationsByCompany(token, companyId)
                }
                .onFailure { e ->
                    Log.e("CompanyVM", "❌ Error al eliminar publicación: ${e.message}")
                }
        }
    }



///////////////////////////////  -------------  PARA ELIMINAR UNA PUBLICACION --------  ///////////////////////



///////////////////////////////  -------------  PARA crear UNA PUBLICACION --------  ///////////////////////


    fun createPublication(
        file: String,
        paid: Boolean,
        idLocation: Int,
        idArea: Int
    ) = viewModelScope.launch {
        val token = tokenC.value ?: return@launch
        val companyId = companyIdC.value ?: return@launch

        val input = CompanyPublicationInput(
            file = file,
            paid = paid,
            idLocation = idLocation,
            idArea = idArea,
            idCompany = companyId
        )

        // Imprimir valores del body antes de enviar
        Log.d("CompanyVM", "Creando publicación con body: $input")

        val result = repository.createPublication(token, input)
        if (result.isSuccess) {
            Log.d("CompanyVM", "Publicación creada exitosamente.")
        } else {
            Log.e(
                "CompanyVM",
                "Error al crear publicación: ${result.exceptionOrNull()?.message}"
            )
        }
    }



///////////////////////////////  -------------  PARA crear UNA PUBLICACION --------  ///////////////////////



///////////////////////////////  -------------  PARA ver las lista de mensaje de la compañia --------  ///////////////////////

    private val _messagesC = MutableStateFlow<List<MessageResponseC>>(emptyList())
    val messagesC: StateFlow<List<MessageResponseC>> = _messagesC

    private val _messagesErrorC = MutableStateFlow<String?>(null)
    val messagesErrorC: StateFlow<String?> = _messagesErrorC


    fun fetchMessagesByCompany() = viewModelScope.launch {
        val compId = companyIdC.value ?: return@launch
        repository.findMessagesByCompany(compId)
            .onSuccess { list ->
                _messagesC.value = list
                _messagesErrorC.value = null
                Log.d("CompanyVM", "Messages: $list")
            }
            .onFailure { ex ->
                _messagesErrorC.value = ex.message
                Log.e("CompanyVM", "Error fetching messages", ex)
            }
    }

///////////////////////////////  -------------  PARA ver las lista de mensaje de la compañia --------  ///////////////////////


///////////////////////////////  -------------  PARA OBTENER LAS REDES DE LA COMPAÑIA --------  ///////////////////////




    // Dentro de tu ViewModel, declara:
// Nueva variable en el ViewModel:
    // Nueva variable en el ViewModel:
    private val _companySocialNetworks = MutableStateFlow<List<SocialNetwork>>(emptyList())
    val companySocialNetworks: StateFlow<List<SocialNetwork>> = _companySocialNetworks

    fun fetchCompanySocialNetworks() = viewModelScope.launch {
        val id = companyIdC.value ?: return@launch
        repository.findSocialNetworksByCompany(id)
            .onSuccess { list ->
                _companySocialNetworks.value = list
                if (list.isNotEmpty()) {
                    Log.d("CompanyVM", "Redes sociales obtenidas: $list")
                } else {
                    Log.d("CompanyVM", "No se encontraron redes sociales para companyId=$id")
                }
            }
            .onFailure { ex ->
                _companySocialNetworks.value = emptyList()
                Log.d("CompanyVM", "Error al cargar redes sociales: ${ex.message}")
            }
    }




///////////////////////////////  -------------  PARA OBTENER LAS REDES DE LA COMPAÑIA --------  ///////////////////////



///////////////////////////////  -------------  PARA EDITAR TODOO DE LA COMPAÑIA --------  ///////////////////////
// Dentro de tu ViewModel (por ejemplo EditCompanyViewModel):

    fun confirmEditCompany(
        companyNameText: String,
        companyDescriptionText: String,
        logoLinkText: String,
        socialList: List<SocialNetwork>
    ) = viewModelScope.launch {
        val token = tokenC.value ?: run {
            Log.e("EditCompany", "Token nulo")
            return@launch
        }
        val userId = userIdC.value ?: run {
            Log.e("EditCompany", "userId nulo")
            return@launch
        }
        val companyId = companyIdC.value ?: run {
            Log.e("EditCompany", "companyId nulo")
            return@launch
        }

        // Imprimir todas las redes sociales que se pasan
        socialList.forEach { sn ->
            Log.d("EditCompany", "Red social pasada → id=${sn.idSocialNetwork}, link=${sn.link}")
        }

        // 1) Actualizar Company
        val companyInput = CompanyInputCM(
            companyName = companyNameText,
            companyDescription = companyDescriptionText
        )
        val companyResult = repository.updateCompany(token, companyId, companyInput)
        if (companyResult.isSuccess) {
            Log.d("EditCompany", "✅ Company (id=$companyId) actualizado correctamente")
        } else {
            Log.e(
                "EditCompany",
                "❌ Error al actualizar Company: ${companyResult.exceptionOrNull()?.message}"
            )
        }

        // 2) Actualizar imagen de usuario
        val userImgInput = UserImgInputCM(img = logoLinkText)
        val imgResult = repository.updateUserImg(token, userId, userImgInput)
        if (imgResult.isSuccess) {
            Log.d("EditCompany", "✅ Imagen de usuario (userId=$userId) actualizada correctamente")
        } else {
            Log.e(
                "EditCompany",
                "❌ Error al actualizar imagen: ${imgResult.exceptionOrNull()?.message}"
            )
        }

        // 3) Iterar y actualizar cada red social
        socialList.forEach { sn ->
            val snInput = SocialNetworkInputRS(link = sn.link)
            val snResult = repository.updateSocialNetwork(token, sn.idSocialNetwork, snInput)
            if (snResult.isSuccess) {
                Log.d(
                    "EditCompany",
                    "✅ Red social id=${sn.idSocialNetwork} actualizada correctamente"
                )
            } else {
                Log.e(
                    "EditCompany",
                    "❌ Error al actualizar red id=${sn.idSocialNetwork}: ${snResult.exceptionOrNull()?.message}"
                )
            }
        }
    }


///////////////////////////////  -------------  PARA EDITAR TODOO DE LA COMPAÑIA --------  ///////////////////////








}
