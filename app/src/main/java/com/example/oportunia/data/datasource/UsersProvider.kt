package com.example.oportunia.data.datasource


import com.example.oportunia.domain.model.Users
import java.time.LocalDate

/**
 * This class simulates the interaction with static user data.
 */
class UsersProvider {
    companion object {
        private val userList = listOf(
            Users(
                id = 1,
                email = "admin@oportunia.com",
                password = "123",
                img = null,
                creationDate = LocalDate.of(2024, 1, 10),
                roleId = 1 // Administrator
            ),
            Users(
                id = 2,
                email = "company@oportunia.com",
                password = "123",
                img = null,
                creationDate = LocalDate.of(2024, 2, 15),
                roleId = 2 // Compay
            ),
            Users(
                id = 3,
                email = "student@oportunia.com",
                password = "123",
                img = null,
                creationDate = LocalDate.of(2024, 3, 5),
                roleId = 3 // Student
            ),
            Users(
                id = 4,
                email = "gabriel@gmail.com",
                password = "123",
                img = null,
                creationDate = LocalDate.of(2024, 3, 5),
                roleId = 3 // Student
            ),

        )

        /**
         * Finds a user by their ID.
         * @param id The ID of the user to find.
         * @return The user with the given ID, or null if not found.
         */
        fun findUserById(id: Int): Users? {
            return userList.find { it.id == id }
        }

        /**
         * Finds all users.
         * @return A list of all users.
         */
        fun findAllUsers(): List<Users> {
            return userList
        }
    }
}
