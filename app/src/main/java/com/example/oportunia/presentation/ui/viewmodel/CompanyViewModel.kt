package com.example.oportunia.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.data.remote.dto.PublicationFilterDTO
import com.example.oportunia.domain.model.CompanyWithNetworks
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.oportunia.domain.repository.CompanyRepository
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


    fun fetchCompanyWithNetworks(idCompany: Int) = viewModelScope.launch {
        repository.findCompanyWithNetworks(idCompany)
            .onSuccess { dto ->
                _companyWithNetworks.value = dto

                // Imprime toda la info en Logcat
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





}
