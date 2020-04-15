package org.josh.springbootboardexample.interfaces.boundaries.http

import org.josh.springbootboardexample.application.service.user.AccountService
import org.josh.springbootboardexample.interfaces.dto.UserDto
import org.josh.springbootboardexample.interfaces.dto.UserPostDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/users"])
class UserRestController(
    private val accountService: AccountService
) {
    @GetMapping("/{userId}")
    fun getUser(
        @PathVariable userId: Long
    ): UserDto {
        return accountService.getAccountDetail(userId)
    }

    @PostMapping
    fun signUp(
        @RequestBody body: UserPostDto
    ): UserDto {
        return accountService.signUp(body.email, body.displayName, body.fullName)
    }
}
