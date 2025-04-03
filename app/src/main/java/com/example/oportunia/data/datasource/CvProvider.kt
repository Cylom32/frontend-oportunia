package com.example.oportunia.data.datasource

import com.example.oportunia.domain.model.Cv

class CvProvider {
    companion object {
        private val cvList = mutableListOf<Cv>()


        fun findCvById(id: Int): Cv? {
            return cvList.find { it.id == id }
        }

        fun findCvByStudentId(studentId: Int): Cv? {
            return cvList.find { it.studentId == studentId }
        }

        fun getCvByStudentId(studentId: Int): Cv? {
            return cvList.find { it.studentId == studentId }
        }

        fun findAllCvs(): List<Cv> {
            return cvList
        }

        fun findAllCvByStudentId(studentId: Int): List<Cv> {
            return cvList.filter { it.studentId == studentId }
        }

        fun insertCv(cv: Cv) {
            cvList.add(cv)
        }

        fun updateCv(cv: Cv): Boolean {
            val index = cvList.indexOfFirst { it.id == cv.id }
            return if (index != -1) {
                cvList[index] = cv
                true
            } else {
                false
            }
        }

        fun deleteCv(cv: Cv): Boolean {
            return cvList.removeIf { it.id == cv.id }
        }

        fun changeStatusById(cvId: Int): Boolean {
            val index = cvList.indexOfFirst { it.id == cvId }
            return if (index != -1) {
                val current = cvList[index]
                cvList[index] = current.copy(status = !current.status)
                true
            } else {
                false
            }
        }

    }
}
