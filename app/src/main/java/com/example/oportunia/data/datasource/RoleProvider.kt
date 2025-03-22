package com.example.oportunia.data.datasource


import com.example.oportunia.domain.model.Role

/**
 * This class simulates the interaction with static role data.
 */
class RoleProvider {
    companion object {
        private val roleList = listOf(
            Role(id = 1, name = "Administrator"),
            Role(id = 2, name = "Company"),
            Role(id = 3, name = "Student")
        )

        /**
         * Finds a role by its ID.
         * @param id The ID of the role to find.
         * @return The role with the given ID, or null if not found.
         */
        fun findRoleById(id: Int): Role? {
            return roleList.find { it.id == id }
        }

        /**
         * Finds all roles.
         * @return A list of all roles.
         */
        fun findAllRoles(): List<Role> {
            return roleList
        }
    }
}
