package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.Company

/**
 * This interface represents the CompanyRepository.
 */
interface CompanyRepository {
    suspend fun findAllCompanies(): Result<List<Company>>
    suspend fun findCompanyById(companyId: Long): Result<Company>
}