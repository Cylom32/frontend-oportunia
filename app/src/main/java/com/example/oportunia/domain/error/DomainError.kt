package com.example.oportunia.domain.error

/**
 * Domain error class for handling errors in the domain layer
 */

sealed class DomainError: Exception() {
    data class AreaError(override val message: String):DomainError()
    data class CompanyError(override val message: String):DomainError()
    data class CvError(override val message: String):DomainError()
    data class InboxError(override val message: String):DomainError()
    data class LocationError(override val message: String):DomainError()
    data class MessageError(override val message: String):DomainError()
    data class PublicationError(override val message: String):DomainError()
    data class RoleError(override val message: String):DomainError()
    data class SecurityError(override val message: String):DomainError()
    data class StudentError(override val message: String):DomainError()
    data class UniversityError(override val message: String):DomainError()
    data class UserError(override val message: String):DomainError()

    data object UnknownError : DomainError() {
        override val message: String = "An unknown error occurred"
    }
}