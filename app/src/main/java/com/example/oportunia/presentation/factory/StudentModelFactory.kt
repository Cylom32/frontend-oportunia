package com.example.oportunia.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.oportunia.domain.repository.StudentRepository
import com.example.oportunia.domain.repository.UsersRepository
import com.example.oportunia.ui.viewmodel.StudentViewModel
import com.example.oportunia.ui.viewmodel.UsersViewModel

class StudentViewModelFactory(
    private val studentRepository: StudentRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(studentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
