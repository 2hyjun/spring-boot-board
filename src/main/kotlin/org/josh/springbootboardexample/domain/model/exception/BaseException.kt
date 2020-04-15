package org.josh.springbootboardexample.domain.model.exception

import org.springframework.http.HttpStatus

open class BaseException(
    val code: String,
    val internalMessage: String,
    val httpStatus: HttpStatus
) : RuntimeException("$code $internalMessage")
