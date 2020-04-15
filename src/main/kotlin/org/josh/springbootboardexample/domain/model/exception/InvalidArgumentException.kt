package org.josh.springbootboardexample.domain.model.exception

import org.springframework.http.HttpStatus

class InvalidArgumentException(message: String) : BaseException(
    code = "invalid_argument",
    internalMessage = message,
    httpStatus = HttpStatus.BAD_REQUEST
)
