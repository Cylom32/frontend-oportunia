package com.example.oportunia.domain.repository

/**
 * Repository interface for authentication-related operations.
 * Define los métodos para el inicio de sesión, cierre de sesión y gestión de la sesión.
 */
interface AuthRepository {
    /**
     * Autentica un usuario con las credenciales proporcionadas.
     *
     * @param email    El correo del usuario
     * @param password La contraseña del usuario
     * @return [Result] indicando éxito o fallo de la autenticación
     */
    suspend fun login(email: String, password: String): Result<Unit>

    /**
     * Finaliza la sesión autenticada del usuario.
     *
     * @return [Result] indicando éxito o fallo del cierre de sesión
     */
    suspend fun logout(): Result<Unit>

    /**
     * Verifica si el usuario está actualmente autenticado.
     *
     * @return [Result] envolviendo un booleano con el estado de autenticación
     */
    suspend fun isAuthenticated(): Result<Boolean>

    /**
     * Recupera el nombre de usuario del usuario autenticado.
     *
     * @return [Result] envolviendo el correo del usuario o null si no hay sesión
     */
    suspend fun getCurrentUser(): Result<String?>
}
