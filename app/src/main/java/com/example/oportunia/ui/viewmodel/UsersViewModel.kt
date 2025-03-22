package com.example.oportunia.ui.viewmodel



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oportunia.domain.model.Users
import com.example.oportunia.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class UsersState {
    data object Loading : UsersState()
    data class Success(val user: Users) : UsersState()
    data object Empty : UsersState()
    data class Error(val message: String) : UsersState()
}


class UsersViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<UsersState>(UsersState.Empty)
    val userState: StateFlow<UsersState> = _userState

    private val _selectedUser = MutableStateFlow<Users?>(null)
    val selectedUser: StateFlow<Users?> = _selectedUser

    private val _userList = MutableStateFlow<List<Users>>(emptyList())
    val userList: StateFlow<List<Users>> = _userList

    fun selectUserById(userId: Long) {
        viewModelScope.launch {
            repository.findUserById(userId)
                .onSuccess { user ->
                    _selectedUser.value = user
                }
                .onFailure { exception ->
                    Log.e("UserViewModel", "Error fetching user by ID: ${exception.message}")
                }
        }
    }

    fun findAllUsers() {
        viewModelScope.launch {
            repository.findAllUsers()
                .onSuccess { users ->
                    Log.d("UserViewModel", "Total Users: ${users.size}")
                    _userList.value = users
                }
                .onFailure { exception ->
                    Log.e("UserViewModel", "Failed to fetch users: ${exception.message}")
                }
        }
    }
}
