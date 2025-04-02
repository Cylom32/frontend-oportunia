package com.example.oportunia.data.remote.serializer


import com.example.oportunia.data.remote.dto.UsersDTO
import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * Custom JSON deserializer for [UsersDTO] objects.
 *
 * Handles special cases like:
 * - Mixed id formats (string or numeric)
 * - Nullable fields
 * - Date parsing for creationDate
 */
class UsersDeserializer : JsonDeserializer<UsersDTO> {

    /**
     * Deserializes JSON data into a [UsersDTO] object.
     *
     * @param json The JSON element to deserialize
     * @param typeOfT The type of the object to deserialize
     * @param context The deserialization context
     * @return A fully populated [UsersDTO] object
     * @throws JsonParseException if there's an error during parsing
     */
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): UsersDTO {
        val jsonObject = json.asJsonObject

        // Extract the id, handling both integer and string formats
        val idUser = try {
            jsonObject.get("idUser")?.asInt
        } catch (e: Exception) {
            jsonObject.get("idUser")?.asString?.toIntOrNull()
        }

        val email = jsonObject.get("email")?.asString
            ?: throw JsonParseException("Missing required field: email")

        val password = jsonObject.get("password")?.asString
            ?: throw JsonParseException("Missing required field: password")

        val img = jsonObject.get("img")?.let {
            if (it.isJsonNull) null else it.asString
        }

        val creationDate = jsonObject.get("creationDate")?.asString?.let {
            try {
                LocalDate.parse(it, DateTimeFormatter.ISO_DATE)
            } catch (e: Exception) {
                LocalDate.now()
            }
        } ?: LocalDate.now()

        val idRole = try {
            jsonObject.get("idRole")?.asInt
        } catch (e: Exception) {
            jsonObject.get("idRole")?.asString?.toIntOrNull()
        }

        return UsersDTO(
            idUser = idUser,
            email = email,
            password = password,
            img = img,
            creationDate = creationDate,
            idRole = idRole
        )
    }
}