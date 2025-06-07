package com.example.oportunia.data.repository


import com.example.oportunia.data.mapper.CvMapper
import com.example.oportunia.domain.model.Cv
import com.example.oportunia.domain.repository.CvRepository
import kotlinx.coroutines.flow.first

//class CvRepositoryImpl(
//   // private val dataSource: CvDataSource,
//    private val mapper: CvMapper
//) : CvRepository {
//
//    override suspend fun findAllCvs(): Result<List<Cv>> = runCatching {
//        dataSource.getCvs().first().map { dto ->
//            mapper.mapToDomain(dto)
//        }
//    }
//
//    override suspend fun findCvById(cvId: Int): Result<Cv> = runCatching {
//        val dto = dataSource.getCvById(cvId) ?: error("CV not found")
//        mapper.mapToDomain(dto)
//    }
//
//    override suspend fun findCvByStudentId(studentId: Int): Result<Cv> = runCatching {
//        val dto = dataSource.findCvByStudentId(studentId) ?: error("CV not found")
//        mapper.mapToDomain(dto)
//    }
//
//    override suspend fun findAllCvByStudentId(studentId: Int): Result<List<Cv>> = runCatching {
//        dataSource.findAllCvByStudentId(studentId).first().map { dto ->
//            mapper.mapToDomain(dto)
//        }
//    }
//
//    override suspend fun insertCv(cv: Cv): Result<Unit> = runCatching {
//        val dto = mapper.mapToDto(cv)
//        dataSource.insertCv(dto)
//    }
//
//    override suspend fun updateCv(cv: Cv): Result<Unit> = runCatching {
//        val dto = mapper.mapToDto(cv)
//        dataSource.updateCv(dto)
//    }
//
//    override suspend fun deleteCv(cv: Cv): Result<Unit> = runCatching {
//        val dto = mapper.mapToDto(cv)
//        dataSource.deleteCv(dto)
//    }
//
//    override suspend fun changeStatusById(cvId: Int): Result<Unit> = runCatching {
//        dataSource.changeStatusById(cvId)
//    }


//}
