package com.example.oportunia.data.datasource



import com.example.oportunia.domain.model.University

/**
 * This class simulates the interaction with static university data.
 */
class UniversityProvider {
    companion object {
        private val universityList = listOf(
            University(
                idUniversity = 1,
                universityName = "UNA"
            ),
            University(
                idUniversity = 2,
                universityName = "UCR"
            ),
            University(
                idUniversity = 3,
                universityName = "TEC"
            ),
            University(
                idUniversity = 4,
                universityName = "UTN"
            ),
            University(
                idUniversity = 5,
                universityName = "UNED"
            )
        )

        /**
         * Encuentra una universidad por su ID.
         * @param id El ID de la universidad que se desea encontrar.
         * @return La universidad con el ID indicado, o null si no se encuentra.
         */
        fun findUniversityById(id: Int): University? {
            return universityList.find { it.idUniversity == id }
        }

        /**
         * Obtiene todas las universidades.
         * @return Una lista de todas las universidades.
         */
        fun findAllUniversities(): List<University> {
            return universityList
        }
    }
}
