package com.example.oportunia.data.datasource


import com.example.oportunia.data.datasource.model.CvDTO
import com.example.oportunia.data.mapper.CvMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CvDataSourceImpl(
    private val cvMapper: CvMapper = CvMapper()
) : CvDataSource {

    override suspend fun getCvs(): Flow<List<CvDTO>> = flow {
        val cvs = CvProvider.findAllCvs()
        emit(cvs.map { cvMapper.mapToDto(it) })
    }

    override suspend fun getCvById(id: Int): CvDTO? {
        val cv = CvProvider.findCvById(id)
        return cv?.let { cvMapper.mapToDto(it) }
    }

    override suspend fun insertCv(cvDTO: CvDTO) {
        val cv = cvMapper.mapToDomain(cvDTO)
        CvProvider.insertCv(cv)
    }

    override suspend fun updateCv(cvDTO: CvDTO) {
        val cv = cvMapper.mapToDomain(cvDTO)
        CvProvider.updateCv(cv)
    }

    override suspend fun deleteCv(cvDTO: CvDTO) {
        val cv = cvMapper.mapToDomain(cvDTO)
        CvProvider.deleteCv(cv)
    }

    override suspend fun findCvByStudentId(studentId: Int): CvDTO? {
        val cv = CvProvider.findCvByStudentId(studentId)
        return cv?.let { cvMapper.mapToDto(it) }
    }

    override suspend fun getCvByStudentId(studentId: Int): CvDTO? {
        val cv = CvProvider.getCvByStudentId(studentId)
        return cv?.let { cvMapper.mapToDto(it) }
    }

    override suspend fun findAllCvByStudentId(studentId: Int): Flow<List<CvDTO>> = flow {
        val cvs = CvProvider.findAllCvByStudentId(studentId)
        emit(cvs.map { cvMapper.mapToDto(it) })
    }

    override suspend fun changeStatusById(cvId: Int) {
        CvProvider.changeStatusById(cvId)
    }
}
