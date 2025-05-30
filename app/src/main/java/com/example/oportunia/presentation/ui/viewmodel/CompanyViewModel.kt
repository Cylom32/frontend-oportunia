package com.example.oportunia.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.data.remote.dto.PublicationByCompanyDTO
import com.example.oportunia.data.remote.dto.PublicationFilterDTO
import com.example.oportunia.domain.model.CompanyWithNetworks
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


///////////////////////////////  -------------  PARA GUARDAR EL ID DE LA COMPAÑIA SELECCIONADA EN EL SCROLL   --------  ///////////////////////



    private val _selectedCompanyId = MutableStateFlow<Int?>(null)
    val selectedCompanyId: StateFlow<Int?> = _selectedCompanyId


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


                val json = Gson().toJson(publications)
                Log.d("CompanyVM", json)
            }
            .onFailure { ex ->
                _publicationsError.value = ex.message
            }
    }


///////////////////////////////  -------------  para obtener las publicaciones de la compañia segund id   --------  ///////////////////////






}
