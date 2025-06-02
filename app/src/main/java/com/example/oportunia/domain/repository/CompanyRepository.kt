package com.example.oportunia.domain.repository

import com.example.oportunia.data.remote.dto.CVListResponse
import com.example.oportunia.data.remote.dto.CompanyResponseU
import com.example.oportunia.data.remote.dto.CompanyWithoutIdDTO
import com.example.oportunia.data.remote.dto.InboxInput
import com.example.oportunia.data.remote.dto.InboxResult
import com.example.oportunia.data.remote.dto.PublicationByCompanyDTO
import com.example.oportunia.data.remote.dto.PublicationDetailDTO
import com.example.oportunia.data.remote.dto.PublicationFilterDTO
import com.example.oportunia.domain.model.Company
import com.example.oportunia.domain.model.CompanyWithNetworks
import com.example.oportunia.domain.model.MessageInput
import com.example.oportunia.domain.model.MessageResponseS
import com.example.oportunia.domain.model.UserResponseCompany

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

    suspend fun findInboxByCompany(companyId: Int): Result<InboxResult>

    /**
     * Obtiene la lista de CVs de un estudiante (requiere token).
     */
    suspend fun findCvListByStudent(
        token: String,
        studentId: Int
    ): Result<List<CVListResponse>>

    suspend fun sendMessage(
        token: String,
        input: MessageInput
    ): Result<Unit>


    suspend fun findMessagesByStudent(
        token: String,
        studentId: Int
    ): Result<List<MessageResponseS>>


    suspend fun findCompanyByUser(userId: Int): Result<CompanyResponseU>


    suspend fun findSocialNetworksByCompany(companyId: Int): Result<List<com.example.oportunia.domain.model.SocialNetwork>>


    suspend fun findUserCompanyById(token: String, userIdCompany: Int): Result<UserResponseCompany>

    suspend fun deletePublicationById(token: String, publicationId: Int): Result<Unit>



//    suspend fun saveCompanyNoId(dto: CompanyWithoutIdDTO): Result<Company>
//    suspend fun insertCompany(company: Company): Result<Unit>
//    suspend fun updateCompany(company: Company): Result<Unit>
//    suspend fun deleteCompany(id: Int): Result<Unit>
//    suspend fun findCompanyWithNetworks(id: Int): Result<CompanyWithNetworks>
}
