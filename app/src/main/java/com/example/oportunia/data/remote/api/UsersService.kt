package com.example.oportunia.data.remote.api


import com.example.oportunia.data.remote.dto.AreaDTO
import com.example.oportunia.data.remote.dto.LocationDTO
import com.example.oportunia.data.remote.dto.UserEmailResponse
import com.example.oportunia.data.remote.dto.UserResponseDTO
import com.example.oportunia.data.remote.dto.UsersDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit service interface that defines the API endpoints for user operations.
 * This service interacts with the remote user API using HTTP methods.
 */
interface UsersService {

    /**
     * Retrieves all users from the remote API.
     *
     * @return [Response] containing a list of [UsersDTO] objects if successful
     */
    @GET("users")
    suspend fun getAllUsers(): Response<List<UsersDTO>>

    /**
     * Retrieves a specific user by their unique identifier.
     *
     * @param id The unique identifier of the user to retrieve
     * @return [Response] containing the requested [UsersDTO] if successful
     */
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<UsersDTO>

    /**
     * Creates a new user in the remote API.
     *
     * @param user The [UsersDTO] object containing the user data to create
     * @return [Response] containing the created [UsersDTO] with server-assigned ID if successful
     */
    @POST("v1/users")
    suspend fun createUser(@Body user: UsersDTO): Response<UserResponseDTO>




    /**
     * Updates an existing user in the remote API.
     *
     * @param id The unique identifier of the user to update
     * @param user The [UsersDTO] object containing the updated user data
     * @return [Response] containing the updated [UsersDTO] if successful
     */
    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body user: UsersDTO
    ): Response<UsersDTO>

    /**
     * Deletes a user from the remote API.
     *
     * @param id The unique identifier of the user to delete
     * @return [Response] indicating the success of the operation
     */
    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Unit>


    @GET("users")
    suspend fun loginUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<List<UsersDTO>>


    @GET("v1/users/email/{email}")
    suspend fun getUserByEmail(
        @Path("email") email: String
    ): Response<UserEmailResponse>




    @GET("v1/areas")
    suspend fun getAreas(): Response<List<AreaDTO>>


    @GET("v1/locations")
    suspend fun getAllLocations(): Response<List<LocationDTO>>



}