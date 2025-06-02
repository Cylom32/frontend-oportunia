package com.example.oportunia.presentation.factory


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.oportunia.domain.repository.CompanyRepository
import com.example.oportunia.domain.repository.UniversityRepository
import com.example.oportunia.domain.repository.UsersRepository
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel


/**
 * Factory for creating UsersViewModel instances with the required repository.
 *
 * @param userRepository The repository that provides access to user data
 */
class UsersViewModelFactory(
    private val usersRepository: UsersRepository,
    private val companyRepository: CompanyRepository,
    private val universityRepository: UniversityRepository,  // ← inyecta también el repo de universidades
    private val appContext: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(
                usersRepository,
                companyRepository,
                universityRepository,  // ← pásaselo al ViewModel
                appContext
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}




