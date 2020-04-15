package org.josh.springbootboardexample.interfaces.dto

data class UserDto(
    val id: Long,
    val displayName: String,
    val fullName: String,
    val email: String,
    val boards: List<BoardListDto>
)

data class UserPostDto(
    val displayName: String,
    val fullName: String,
    val email: String
)
