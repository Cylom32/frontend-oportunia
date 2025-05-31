// data/remote/repository/CompaniesRepositoryImpl.kt
package com.example.oportunia.data.remote.repository

import com.example.oportunia.data.mapper.CompanyMapper
import com.example.oportunia.data.remote.CompaniesRemoteDataSource
import com.example.oportunia.data.remote.dto.CVListResponse
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
import com.example.oportunia.domain.repository.CompanyRepository
import java.net.UnknownHostException
import javax.inject.Inject

class CompaniesRepositoryImpl @Inject constructor(
    private val remoteDataSource: CompaniesRemoteDataSource,
    private val mapper: CompanyMapper
) : CompanyRepository {

    override suspend fun findAllCompanies(): Result<List<Company>> {
        return try {
            remoteDataSource.findAllCompanies().map { dtoList ->
                dtoList.map { mapper.mapToDomain(it) }
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: unable to connect to server."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching companies: ${e.message}"))
        }
    }

    override suspend fun findCompanyById(id: Int): Result<Company> {
        return try {
            remoteDataSource.findCompanyById(id).map { dto ->
                mapper.mapToDomain(dto)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching company with id=$id: ${e.message}"))
        }
    }

    override suspend fun findInboxByCompany(companyId: Int): Result<InboxResult> =
        remoteDataSource.findInboxByCompany(companyId)


    override suspend fun findPublicationsByFilter(
        areaId: Int?,
        locationId: Int?,
        paid: Boolean?
    ): Result<List<PublicationFilterDTO>> =
        try {
            remoteDataSource.findPublicationsByFilter(areaId, locationId, paid)
        } catch (e: Exception) {
            Result.failure(Exception("Error al obtener publicaciones: ${e.message}"))
        }



    override suspend fun findCompanyWithNetworks(id: Int): Result<CompanyWithNetworks> {
        return try {
            val dto = remoteDataSource.findWithNetworks(id)
            val domain = mapper.mapToDomainWithNetworks(dto)
            Result.success(domain)
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching company with networks id=$id: ${e.message}"))
        }
    }


    override suspend fun findPublicationsByCompany(
        token: String,
        companyId: Int
    ): Result<List<PublicationByCompanyDTO>> =
        try {
            remoteDataSource.findPublicationsByCompany(token, companyId)
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching publications by company: ${e.message}"))
        }

    override suspend fun findPublicationById(id: Int): Result<PublicationDetailDTO> {
        return try {
            remoteDataSource.findPublicationById(id)
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: unable to connect to server."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching publication with id=$id: ${e.message}"))
        }
    }

    override suspend fun findCvListByStudent(
        token: String,
        studentId: Int
    ): Result<List<CVListResponse>> = try {
        // delegamos al remoteDataSource
        remoteDataSource.findCvListByStudent(token, studentId)
    } catch (e: Exception) {
        Result.failure(Exception("Error fetching CV list for student id=$studentId: ${e.message}"))
    }


    override suspend fun sendMessage(
        token: String,
        input: MessageInput
    ): Result<Unit> =
        try {
            remoteDataSource.sendMessage(token, input)
        } catch (e: Exception) {
            Result.failure(Exception("Error sending message: ${e.message}"))
        }



    override suspend fun findMessagesByStudent(
        token: String,
        studentId: Int
    ): Result<List<MessageResponseS>> =
        try {
            remoteDataSource.findMessagesByStudent(token, studentId)
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching messages by student id=$studentId: ${e.message}"))
        }

}



//
//    override suspend fun saveCompanyNoId(dto: CompanyWithoutIdDTO): Result<Company> {
//        return try {
//            remoteDataSource.createCompany(dto)
//                .map { createdDto -> mapper.mapToDomain(createdDto) }
//        } catch (e: Exception) {
//            Result.failure(Exception("Error creating company without ID: ${e.message}"))
//        }
//    }
//
//    override suspend fun insertCompany(company: Company): Result<Unit> {
//        return try {
//            val dto = mapper.mapToDtoWithoutId(company)
//            remoteDataSource.createCompany(dto)
//            Result.success(Unit)
//        } catch (e: Exception) {
//            Result.failure(Exception("Error creating company: ${e.message}"))
//        }
//    }
//
//    override suspend fun updateCompany(company: Company): Result<Unit> {
//        return try {
//            val dto = mapper.mapToDtoWithoutId(company)
//            remoteDataSource.updateCompany(company.id, dto)
//            Result.success(Unit)
//        } catch (e: Exception) {
//            Result.failure(Exception("Error updating company id=${company.id}: ${e.message}"))
//        }
//    }
//
//    override suspend fun deleteCompany(id: Int): Result<Unit> {
//        return try {
//            remoteDataSource.deleteCompany(id)
//            Result.success(Unit)
//        } catch (e: Exception) {
//            Result.failure(Exception("Error deleting company id=$id: ${e.message}"))
//        }
//    }
//
//    override suspend fun findCompanyWithNetworks(id: Int): Result<CompanyWithNetworks> {
//        return try {
//            remoteDataSource.findWithNetworks(id)
//                .map { dto -> mapper.mapToDomainWithNetworks(dto) }
//        } catch (e: Exception) {
//            Result.failure(Exception("Error fetching company with networks id=$id: ${e.message}"))
//        }
//    }
