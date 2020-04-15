package org.josh.springbootboardexample.domain.model.board

import org.josh.springbootboardexample.domain.model.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long> {
    fun findAllByUser(user: User): List<Board>
}
