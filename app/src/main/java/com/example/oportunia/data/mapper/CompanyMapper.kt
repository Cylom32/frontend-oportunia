// data/mapper/CompanyMapper.kt
package com.example.oportunia.data.mapper

import com.example.oportunia.data.remote.dto.CompanyDTO
import com.example.oportunia.data.remote.dto.CompanyWithNetworksDTO
import com.example.oportunia.domain.model.Company
import com.example.oportunia.domain.model.CompanyWithNetworks
import com.example.oportunia.domain.model.SocialNetwork
import javax.inject.Inject

class CompanyMapper @Inject constructor() {

    /** Convierte el DTO de red a tu modelo de dominio */
    fun mapToDomain(dto: CompanyDTO): Company = Company(
        id                = dto.idCompany ?: 0,
        companyName       = dto.companyName,
        companyDescription = dto.companyDescription ?: ""    // evita String? → String
    )

    /**
     * Convierte tu modelo de dominio + userId al DTO completo (incluye ID para update)
     * @param userId debe provenir de tu lógica de negocio (p. ej. SharedPreferences o auth)
     */
    fun mapToDto(domain: Company, userId: Int): CompanyDTO = CompanyDTO(
        idCompany         = domain.id,
        companyName       = domain.companyName,
        companyDescription = domain.companyDescription,
        idUser            = userId
    )

    fun mapToDomainWithNetworks(dto: CompanyWithNetworksDTO): CompanyWithNetworks =
        CompanyWithNetworks(
            idCompany          = dto.idCompany,
            companyName        = dto.companyName,
            companyDescription = dto.companyDescription,
            socialNetworks     = dto.socialNetworks.map { snDto ->
                SocialNetwork(
                    idSocialNetwork = snDto.idSocialNetwork,
                    link            = snDto.link
                )
            }
        )


}
