package com.example.oportunia.data.remote.serializer

import com.example.oportunia.data.remote.dto.StudentDTO
import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDate

/**
 * Custom Gson deserializer for [StudentDTO], manejando:
 * - idStudent tanto numérico como string
 * - idUser y idUniversity anidados o directos
 * - firstName/name, lastName1 y lastName2
 * - creationDate como String "YYYY-MM-DD" a LocalDate
 */
class StudentDeserializer : JsonDeserializer<StudentDTO> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): StudentDTO {
        val obj = json.asJsonObject

        // idStudent: Int o String
        val idStudent: Int? = runCatching { obj.get("idStudent")?.asInt }
            .getOrElse { obj.get("idStudent")?.asString?.toIntOrNull() }

        // idUser: busca en objeto "user" o directo "idUser"
        val idUser: Int? = obj.getAsJsonObject("user")
            ?.get("idUser")?.asInt
            ?: obj.get("idUser")?.asString?.toIntOrNull()

        // firstName o name obligatorio
        val name: String = when {
            obj.has("firstName") -> obj.get("firstName").asString
            obj.has("name") -> obj.get("name").asString
            else -> throw JsonParseException("Missing field 'firstName' or 'name'")
        }

        // lastName1 obligatorio
        val lastName1: String = obj.get("lastName1")?.asString
            ?: throw JsonParseException("Missing field 'lastName1'")

        // lastName2 opcional (nullable)
        val lastName2: String? = obj.get("lastName2")
            ?.takeIf { !it.isJsonNull }?.asString

        // creationDate: String "YYYY-MM-DD" o fecha de hoy si falta
        val creationDateStr: String = obj.get("creationDate")?.asString
            ?: LocalDate.now().toString()
        val creationDate: LocalDate = try {
            LocalDate.parse(creationDateStr)
        } catch (e: Exception) {
            throw JsonParseException("Invalid date format for 'creationDate': $creationDateStr")
        }

        // idUniversity: busca en objeto "university" o directo "idUniversity"
        val idUniversity: Int? = obj.getAsJsonObject("university")
            ?.get("idUniversity")?.asInt
            ?: obj.get("idUniversity")?.asString?.toIntOrNull()

        return StudentDTO(
            idStudent   = idStudent,
            idUser      = idUser,
            name        = name,
            lastName1   = lastName1,
            lastName2   = lastName2,
            creationDate = creationDate,
            idUniversity = idUniversity
        )
    }
}
