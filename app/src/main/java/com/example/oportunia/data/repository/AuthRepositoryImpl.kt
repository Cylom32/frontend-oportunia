package com.example.oportunia.data.repository


import android.util.Log
import com.example.oportunia.data.local.AuthPreferences
import com.example.oportunia.data.remote.AuthRemoteDataSource
import com.example.oportunia.domain.model.Credentials
import com.example.oportunia.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authPreferences: AuthPreferences
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            Log.d("AuthRepositoryImpl", "Login attempt for user: $email")
            val credentials = Credentials(email, password)

            authRemoteDataSource.login(credentials)
                .onSuccess { authResult ->
                    val token = authResult.token
                    if (token.isNotBlank()) {
                        Log.d(
                            "AuthRepositoryImpl",
                            "Login successful, token: ${token.take(10)}…"
                        )
                        authPreferences.saveAuthToken(token)
                        authPreferences.saveUsername(email)
                    } else {
                        Log.e("AuthRepositoryImpl", "Received empty token")
                    }
                }
                .onFailure { error ->
                    Log.e("AuthRepositoryImpl", "Login failed: ${error.message}")
                }
                .map { }
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Login exception: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            Log.d("AuthRepositoryImpl", "Attempting to logout")
            authRemoteDataSource.logout()
                .onSuccess {
                    Log.d("AuthRepositoryImpl", "Remote logout successful")
                }
                .onFailure { error ->
                    Log.e("AuthRepositoryImpl", "Remote logout failed: ${error.message}")
                }

            authPreferences.clearAuthData()
            Log.d("AuthRepositoryImpl", "Local auth data cleared")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Logout exception: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun isAuthenticated(): Result<Boolean> {
        return try {
            val token = authPreferences.getAuthToken()
            val authenticated = !token.isNullOrEmpty()
            Log.d("AuthRepositoryImpl", "Authentication check: $authenticated")
            Result.success(authenticated)
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Auth check exception: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): Result<String?> {
        return try {
            val user = authPreferences.getUsername()
            Log.d("AuthRepositoryImpl", "Current user: $user")
            Result.success(user)
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Get current user exception: ${e.message}", e)
            Result.failure(e)
        }
    }
}
