package org.josh.springbootboardexample.domain.model.user

import java.util.regex.Pattern
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table
import org.josh.springbootboardexample.domain.model.exception.InvalidArgumentException
import org.josh.springbootboardexample.domain.model.shard.DomainEntity

@Entity
@Table(
    name = "users",
    indexes = [
        Index(name = "idx_user_email", columnList = "email", unique = true)
    ]
)
class User(
    @Column
    val displayName: String,

    @Column
    val fullName: String,

    @Column
    val email: String
) : DomainEntity() {

    // no-args constructor is necessary for hibernate
    constructor() : this(
        displayName = "",
        fullName = "",
        email = ""
    )

    // TODO: specification 으로 빼기
    fun validate() {
        email.takeIf(::isValidEmail) ?: throw InvalidArgumentException("Invalid Email $email")
        fullName.takeIf(::isValidFullName) ?: throw InvalidArgumentException("Invalid fullName $fullName")
        displayName.takeIf(::isValidDisplayName) ?: throw InvalidArgumentException("Invalid fullName $fullName")
    }

    private fun isValidEmail(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@" +
                "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" +
                "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." +
                "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" +
                "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|" +
                "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    private fun isValidFullName(fullName: String): Boolean {
        return fullName.length in 3..20
    }

    private fun isValidDisplayName(displayName: String): Boolean {
        return displayName.length in 3..10
    }
}
