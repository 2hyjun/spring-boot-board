package org.josh.springbootboardexample.application.service.user

import org.josh.springbootboardexample.application.service.board.BoardService
import org.josh.springbootboardexample.domain.model.exception.NotFoundException
import org.josh.springbootboardexample.domain.model.user.User
import org.josh.springbootboardexample.domain.model.user.UserRepository
import org.josh.springbootboardexample.interfaces.dto.BoardListDto
import org.josh.springbootboardexample.interfaces.dto.UserDto
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val userRepository: UserRepository,
    private val boardService: BoardService
) {
    fun getAccount(userId: Long): User {
        return userRepository.findById(userId).orElseThrow { NotFoundException("user#$userId not found") }
    }

    fun getAccountDetail(userId: Long): UserDto {
        return userRepository.findById(userId).orElseThrow { NotFoundException("user#$userId not found") }
            .let {
                val boards = boardService.getBoardsByUser(it)
                it.toDto(boards)
            }
    }

    fun signUp(email: String, displayName: String, fullName: String): UserDto {
        val user = User(displayName, fullName, email).apply { validate() }

        return userRepository.save(user).toDto()
    }

    private fun User.toDto(boards: List<BoardListDto> = listOf()) = UserDto(
        id, displayName, fullName, email, boards
    )
}
