package org.josh.springbootboardexample.interfaces.boundaries.http

import org.josh.springbootboardexample.domain.model.exception.BaseException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class BoardControllerAdvice : ResponseEntityExceptionHandler() {
    private val slf4jLogger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(value = [])
    protected fun handleError(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        val status: HttpStatus
        val code: String
        val message: String
        if (ex is BaseException && ex.code != "internal_server_error") {
            status = ex.httpStatus
            code = ex.code
            message = ex.internalMessage

            slf4jLogger.info(ex.internalMessage)
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR
            code = "internal_server_error"
            message = "Internal Server Error"
        }

        if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            slf4jLogger.error("internal_error", ex)
        }

        val responseBody = mapOf(
            "code" to code,
            "message" to message,
            "error" to if (status.value() >= 400) code else null
        )

        return handleExceptionInternal(ex, responseBody, HttpHeaders.EMPTY, status, request)
    }
}
