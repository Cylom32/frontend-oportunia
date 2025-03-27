package com.example.oportunia.data.datasource

import com.example.oportunia.domain.model.Student
import java.time.LocalDate

class StudentProvider {
    companion object {
        private val studentList = mutableListOf(
            Student(
                idStudent = 1,
                idUser = 3, // Asociado al usuario "student@oportunia.com"
                name = "Wilson",
                lastName1 = "Muñoz",
                lastName2 = "Gracia",
                creationDate = LocalDate.of(2024, 3, 5),
                universityId = 1
            ),
            Student(
                idStudent = 2,
                idUser = 4, // Asociado al usuario "gabriel@gmail.com"
                name = "Gabriel",
                lastName1 = "Chavarría",
                lastName2 = "Calero ",
                creationDate = LocalDate.of(2024, 3, 6),
                universityId = 2
            )
        )

        /**
         * Finds a student by their ID.
         * @param id The ID of the student to find.
         * @return The student with the given ID, or null if not found.
         */
        fun findStudentById(id: Int): Student? {
            return studentList.find { it.idStudent == id }
        }

        /**
         * Finds a student by their associated user ID.
         * @param userId The user ID linked to the student.
         * @return The student with the given user ID, or null if not found.
         */
        fun findStudentByUserId(userId: Int): Student? {
            return studentList.find { it.idUser == userId }
        }

        /**
         * Finds all students.
         * @return A list of all students.
         */
        fun findAllStudents(): List<Student> {
            return studentList
        }

        /**
         * Inserts a new student into the list.
         * @param student The student to insert.
         */
        fun insertStudent(student: Student) {
            studentList.add(student)
        }
    }
}
