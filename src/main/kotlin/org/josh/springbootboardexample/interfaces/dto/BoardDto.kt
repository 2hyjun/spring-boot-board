package org.josh.springbootboardexample.interfaces.dto

import org.josh.springbootboardexample.domain.model.board.ReactionType
import org.josh.springbootboardexample.domain.model.user.User

data class BoardListDto(
    val id: Long,
    val title: String,
    val content: String,
    val userDisplayName: String,
    val reactionPoints: Int
)

data class BoardDetailDto(
    val id: Long,
    val title: String,
    val content: String,
    val user: User,
    val comments: List<CommentDto>,
    val reactions: List<BoardReactionDto>
)

data class BoardPostDto(
    val title: String,
    val content: String,
    val userId: Long
)

data class PostCommentDto(
    val userId: Long,
    val content: String
)

data class LikeBoardDto(
    val userId: Long,
    val boardId: Long
)

data class CommentDto(
    val content: String,
    val reactions: List<CommentReactionDto>,
    val user: UserDto
)

data class CommentReactionDto(
    val user: UserDto,
    val reactionType: ReactionType
)

data class BoardReactionDto(
    val user: UserDto,
    val reactionType: ReactionType
)
