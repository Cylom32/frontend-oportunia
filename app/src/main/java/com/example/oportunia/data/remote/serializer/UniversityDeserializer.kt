package com.example.oportunia.data.remote.serializer

import com.example.oportunia.data.remote.dto.UniversityDTO
import com.google.gson.*
import java.lang.reflect.Type

/**
 * Custom JSON deserializer for [UniversityDTO] objects.
 *
 * Maneja:
 * - idUniversity como Int o String
 * - Campos obligatorios con excepción si faltan
 * - Añadir más campos siguiendo este patrón
 */
class UniversityDeserializer : JsonDeserializer<UniversityDTO> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): UniversityDTO {
        val jsonObject = json.asJsonObject

        // idUniversity: puede venir como número o cadena
        val idUniversity = try {
            jsonObject.get("idUniversity")?.asInt
        } catch (e: Exception) {
            jsonObject.get("idUniversity")?.asString?.toIntOrNull()
        }

        // universityName: campo obligatorio
        val universityName = jsonObject.get("universityName")?.asString
            ?: throw JsonParseException("Missing required field: universityName")

        // Si hay otros campos en UniversityDTO, extraerlos aquí...
        // val address = jsonObject.get("address")?.asString

        return UniversityDTO(
            idUniversity = idUniversity,
            universityName = universityName
            // , address = address
        )
    }
}
