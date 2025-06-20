package com.example.oportunia.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.oportunia.domain.repository.CompanyRepository
import com.example.oportunia.domain.repository.CvRepository
import com.example.oportunia.domain.repository.StudentRepository
import com.example.oportunia.domain.repository.UniversityRepository
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel


//class StudentViewModelFactory(
//    private val studentRepository: StudentRepository,
//    private val universityRepository: UniversityRepository,
//    private val companyRepository: CompanyRepository
//  //  private val cvRepository: CvRepository
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return StudentViewModel(studentRepository, companyRepository, universityRepository/*, cvRepository*/) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//    }
//}
