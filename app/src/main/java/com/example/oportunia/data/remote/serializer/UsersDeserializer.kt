package com.example.oportunia.data.remote.serializer

import com.example.oportunia.data.remote.dto.UsersDTO
import com.google.gson.*
import java.lang.reflect.Type

/**
 * Custom Gson deserializer for [UsersDTO], manejando:
 * - idUser tanto numérico como string
 * - creationDate como String "YYYY-MM-DD"
 * - img nullable
 * - idRole tanto numérico como string
 */
class UsersDeserializer : JsonDeserializer<UsersDTO> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): UsersDTO {
        val obj = json.asJsonObject

        // idUser: Int o String
        val idUser = runCatching { obj.get("idUser")?.asInt }
            .getOrElse { obj.get("idUser")?.asString?.toIntOrNull() }

        // email y password obligatorios
        val email = obj.get("email")?.asString
            ?: throw JsonParseException("Missing field 'email'")
        val password = obj.get("password")?.asString
            ?: throw JsonParseException("Missing field 'password'")

        // img opcional
        val img = obj.get("img")?.takeIf { !it.isJsonNull }?.asString

        // creationDate como String, o fecha de hoy si falta
        val creationDate = obj.get("creationDate")?.asString
            ?: java.time.LocalDate.now().toString()

        // idRole: Int o String
        val idRole = runCatching { obj.get("idRole")?.asInt }
            .getOrElse { obj.get("idRole")?.asString?.toIntOrNull() }
            ?: throw JsonParseException("Missing field 'idRole'")

        return UsersDTO(
            idUser       = idUser,
            email        = email,
            password     = password,
            img          = img,
            creationDate = creationDate,  // ya es String
            idRole       = idRole
        )
    }
}
