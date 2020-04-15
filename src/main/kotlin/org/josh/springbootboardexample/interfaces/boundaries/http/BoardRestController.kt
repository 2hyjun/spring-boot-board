package org.josh.springbootboardexample.interfaces.boundaries.http

import org.josh.springbootboardexample.application.service.board.BoardService
import org.josh.springbootboardexample.application.service.user.AccountService
import org.josh.springbootboardexample.interfaces.dto.BoardDetailDto
import org.josh.springbootboardexample.interfaces.dto.BoardListDto
import org.josh.springbootboardexample.interfaces.dto.BoardPostDto
import org.josh.springbootboardexample.interfaces.dto.LikeBoardDto
import org.josh.springbootboardexample.interfaces.dto.PostCommentDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/board"])
class BoardRestController(
    private val boardService: BoardService,
    private val accountService: AccountService
) {

    @GetMapping
    fun index(): List<BoardListDto> {
        return boardService.getBoards()
    }

    @GetMapping("/{boardId}")
    fun detail(
        @PathVariable boardId: Long
    ): BoardDetailDto {
        return boardService.getBoardDetail(boardId)
    }

    @PostMapping
    fun post(
        @RequestBody body: BoardPostDto
    ): BoardDetailDto {
        val user = accountService.getAccount(body.userId)
        return boardService.postBoard(user, body.title, body.content)
    }

    @PostMapping("/{boardId}/comment")
    fun postComment(
        @PathVariable boardId: Long,
        @RequestBody body: PostCommentDto
    ): BoardDetailDto {
        val user = accountService.getAccount(body.userId)
        return boardService.postComment(user, boardId, body.content)
    }

    @PostMapping("/{boardId}/like")
    fun likeBoard(
        @PathVariable boardId: Long,
        @RequestBody body: LikeBoardDto
    ): BoardDetailDto {
        val user = accountService.getAccount(body.userId)
        return boardService.likeBoard(user, boardId)
    }
}
