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
class Comment(
    @Column
    val content: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id")
    val board: Board
) : DomainEntity() {
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "comment")
    val reactions: MutableList<CommentReaction> = mutableListOf()

    constructor() : this(
        "",
        User(),
        Board()
    )

    fun addLikes(reaction: CommentReaction) {
        this.reactions.add(reaction)
    }

    fun removeLikes(reaction: CommentReaction) {
        this.reactions.add(reaction)
    }
}
