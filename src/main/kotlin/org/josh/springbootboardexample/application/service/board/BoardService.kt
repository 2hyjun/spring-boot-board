package org.josh.springbootboardexample.application.service.board

import javax.transaction.Transactional
import org.josh.springbootboardexample.domain.model.board.Board
import org.josh.springbootboardexample.domain.model.board.BoardReaction
import org.josh.springbootboardexample.domain.model.board.BoardRepository
import org.josh.springbootboardexample.domain.model.board.Comment
import org.josh.springbootboardexample.domain.model.board.CommentReaction
import org.josh.springbootboardexample.domain.model.board.ReactionType
import org.josh.springbootboardexample.domain.model.exception.NotFoundException
import org.josh.springbootboardexample.domain.model.user.User
import org.josh.springbootboardexample.interfaces.dto.BoardDetailDto
import org.josh.springbootboardexample.interfaces.dto.BoardListDto
import org.josh.springbootboardexample.interfaces.dto.BoardReactionDto
import org.josh.springbootboardexample.interfaces.dto.CommentDto
import org.josh.springbootboardexample.interfaces.dto.CommentReactionDto
import org.josh.springbootboardexample.interfaces.dto.UserDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Transactional
class BoardService(
    private val boardRepository: BoardRepository
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    // TODO: add pagination, filters
    fun getBoards(): List<BoardListDto> {
        return boardRepository.findAll().map { it.toListDto() }
    }

    fun getBoardDetail(boardId: Long): BoardDetailDto {
        return boardRepository.findById(boardId).orElseThrow { NotFoundException("board#$boardId not found") }
            .toDetailDto()
    }

    fun getBoardsByUser(user: User): List<BoardListDto> {
        return boardRepository.findAllByUser(user).map { it.toListDto() }
    }

    fun getBoard(boardId: Long): Board {
        return boardRepository.findById(boardId).orElseThrow { NotFoundException("board#$boardId not found") }
    }

    fun postBoard(user: User, title: String, content: String): BoardDetailDto {
        val newBoard = Board(title, content, user)
        return boardRepository.save(newBoard).toDetailDto()
    }

    fun postComment(user: User, boardId: Long, content: String): BoardDetailDto {
        val board = getBoard(boardId)

        board.addComment(user, content)
        return board.toDetailDto()
    }

    fun likeBoard(user: User, boardId: Long): BoardDetailDto {
        val board = getBoard(boardId)

        board.addLikes(user, ReactionType.LIKE)
        return board.toDetailDto()
    }

    private fun Board.toListDto() = BoardListDto(
        id,
        title,
        content,
        user.displayName,
        reactionPoints
    )

    private fun Board.toDetailDto() = BoardDetailDto(
        id,
        title,
        content,
        user,
        comments.map { it.toDto() },
        reactions.map { it.toDto() }
    )

    private fun Comment.toDto() = CommentDto(
        content,
        reactions.map { it.toDto() },
        user.toDto()
    )

    private fun BoardReaction.toDto() = BoardReactionDto(user.toDto(), reactionType)

    private fun CommentReaction.toDto() = CommentReactionDto(user.toDto(), reactionType)

    private fun User.toDto(boards: List<BoardListDto> = listOf()) = UserDto(
        id, displayName, fullName, email, boards
    )
}
