package com.example.oportunia.domain.repository

import com.example.oportunia.data.remote.dto.CompanyWithoutIdDTO
import com.example.oportunia.data.remote.dto.PublicationByCompanyDTO
import com.example.oportunia.data.remote.dto.PublicationDetailDTO
import com.example.oportunia.data.remote.dto.PublicationFilterDTO
import com.example.oportunia.domain.model.Company
import com.example.oportunia.domain.model.CompanyWithNetworks

interface CompanyRepository {
    suspend fun findAllCompanies(): Result<List<Company>>
    suspend fun findCompanyById(id: Int): Result<Company>
    suspend fun findPublicationsByFilter(
        areaId: Int? = null,
        locationId: Int? = null,
        paid: Boolean? = null
    ): Result<List<PublicationFilterDTO>>

    suspend fun findCompanyWithNetworks(id: Int): Result<CompanyWithNetworks>

    suspend fun findPublicationsByCompany(
        token: String,
        companyId: Int
    ): Result<List<PublicationByCompanyDTO>>

    suspend fun findPublicationById(id: Int): Result<PublicationDetailDTO>

//    suspend fun saveCompanyNoId(dto: CompanyWithoutIdDTO): Result<Company>
//    suspend fun insertCompany(company: Company): Result<Unit>
//    suspend fun updateCompany(company: Company): Result<Unit>
//    suspend fun deleteCompany(id: Int): Result<Unit>
//    suspend fun findCompanyWithNetworks(id: Int): Result<CompanyWithNetworks>
}
