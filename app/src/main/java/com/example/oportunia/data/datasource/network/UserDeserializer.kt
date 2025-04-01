package com.example.oportunia.data.datasource.network

import com.example.oportunia.domain.model.Users
import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDate

/**
 * Custom deserializer for the Users class to handle JSON deserialization.
 */
class UserDeserializer : JsonDeserializer<Users> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Users {
        val jsonObject = json.asJsonObject

        val id = try {
            jsonObject.get("id").asInt
        } catch (e: Exception) {
            jsonObject.get("id").asString.toInt()
        }

        val email = jsonObject.get("email").asString
        val password = jsonObject.get("password").asString
        val img = jsonObject.get("img")?.takeIf { !it.isJsonNull }?.asString

        val creationDateRaw = jsonObject.get("creationDate").asString
        val creationDate = LocalDate.parse(creationDateRaw.substring(0, 10)) // Solo yyyy-MM-dd

        val roleId = jsonObject.get("roleId")?.takeIf { !it.isJsonNull }?.asInt

        return Users(id, email, password, img, creationDate, roleId)
    }
}
