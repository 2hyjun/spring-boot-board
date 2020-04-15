package org.josh.springbootboardexample.domain.model.exception

import org.springframework.http.HttpStatus

class NotFoundException(message: String) : BaseException(
    code = "not_found",
    internalMessage = message,
    httpStatus = HttpStatus.NOT_FOUND
)
