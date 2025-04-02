package com.example.oportunia.presentation.factory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.oportunia.domain.repository.UserRepository
import com.example.oportunia.domain.repository.UsersRepository
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel


/**
 * Factory for creating UsersViewModel instances with the required repository.
 *
 * @param userRepository The repository that provides access to user data
 */
class UsersViewModelFactory(
    private val userRepository: UsersRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}


