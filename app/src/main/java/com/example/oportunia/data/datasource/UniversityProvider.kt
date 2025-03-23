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
            )
        )

        /**
         * Returns all available universities.
         * @return A list of universities.
         */
        fun getAllUniversities(): List<University> {
            return universityList
        }
    }
}
