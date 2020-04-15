package org.josh.springbootboardexample.domain.model.board

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class ReactionType(@JsonValue val str: String, val points: Int) {
    LIKE("like", 1),
    HATE("hate", -1);

    companion object {
        private val map = values().associateBy(ReactionType::str)

        @JsonCreator
        @JvmStatic
        fun fromStr(str: String) = map[str]
    }

    @Converter
    class PersistConverter : AttributeConverter<ReactionType, String> {
        override fun convertToDatabaseColumn(attribute: ReactionType) = attribute.str

        override fun convertToEntityAttribute(dbData: String) = fromStr(dbData)
    }
}
