package org.josh.springbootboardexample.domain.model.board

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import org.josh.springbootboardexample.domain.model.shard.DomainEntity
import org.josh.springbootboardexample.domain.model.user.User

@Entity
class Board(
    @Column(length = 127)
    val title: String,

    @Column(columnDefinition = "text")
    val content: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    val user: User
) : DomainEntity() {
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "board")
    val reactions: MutableList<BoardReaction> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "board")
    val comments: MutableList<Comment> = mutableListOf()

    constructor() : this(
        title = "",
        content = "",
        user = User()
    )

    fun addComment(user: User, content: String) {
        val newComment = Comment(content, user, this)
        this.comments.add(newComment)
    }

    fun addLikes(user: User, reactionType: ReactionType) {
        val newReaction = BoardReaction(this, reactionType, user)
        this.reactions.add(newReaction)
    }

    val reactionPoints
        get() = this.reactions.sumBy { it.reactionType.points }
}
