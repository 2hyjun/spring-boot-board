package org.josh.springbootboardexample.domain.model.board

import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import org.josh.springbootboardexample.domain.model.shard.DomainEntity
import org.josh.springbootboardexample.domain.model.user.User

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
sealed class Reaction(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    open val user: User,

    @Convert(converter = ReactionType.PersistConverter::class)
    open val reactionType: ReactionType
) : DomainEntity() {
    constructor() : this(User(), ReactionType.LIKE)
}

@Entity
class CommentReaction(
    @ManyToOne
    @JoinColumn(name = "comment_id")
    val comment: Comment,

    user: User,
    reactionType: ReactionType
) : Reaction(user, reactionType) {
    constructor() : this(
        comment = Comment(),
        user = User(),
        reactionType = ReactionType.LIKE
    )
}

@Entity
class BoardReaction(
    @ManyToOne
    @JoinColumn(name = "board_id")
    val board: Board,

    reactionType: ReactionType,
    user: User
) : Reaction(user, reactionType) {
    constructor() : this(
        board = Board(),
        user = User(),
        reactionType = ReactionType.LIKE
    )
}
