package com.example.oportunia.data.datasource.network


import com.example.oportunia.domain.model.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit service interface for User-related API endpoints.
 */
interface UserService {

    /**
     * Fetches all users from the API.
     *
     * @return A Response object containing a list of Users.
     */
    @GET("users")
    suspend fun getAllUsers(
        @Query("limit") limit: Int = 100
    ): Response<List<Users>>


    /**
     * Fetches a user by their ID from the API.
     *
     * @param id The ID of the user to fetch.
     * @return A Response object containing the Users object.
     */
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<Users>
}