package com.akotsenko.elevateguard.model

open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class SignInValidateException(
    override val message: String
): AppException(message = message)

class EmptyFieldException(
    val field: Field
) : AppException()

// BackendException with statusCode=401 is usually mapped to this exception
class AuthException(
    cause: Throwable
) : AppException(cause = cause)

class RegisterValidateException(
    override val message: String
): AppException(message = message)

class UserValidateException(
    override val message: String
): AppException(message = message)

class FacilityAlreadyExistException(
    override val message: String
): AppException(message = message)

class FacilityNotFoundException(
    override val message: String
): AppException(message = message)

class ConstructionNotFoundException(
    override val message: String
): AppException(message = message)

class UserNotFoundException(
    override val message: String
): AppException(message = message)

class FailedDeleteUserException(
    override val message: String
): AppException(message = message)

class AccidentNotFoundException(
    override val message: String
): AppException(message = message)

class ConnectionException(cause: Throwable) : AppException(cause = cause)

open class BackendException(
    val code: Int,
    message: String
) : AppException(message)

class ParseBackendResponseException(
    cause: Throwable
) : AppException(cause = cause)

// ---

internal inline fun <T> wrapBackendExceptions(block: () -> T): T {
    try {
        return block.invoke()
    } catch (e: BackendException) {
        if (e.code == 401) {
            throw AuthException(e)
        } else {
            throw e
        }
    }
}