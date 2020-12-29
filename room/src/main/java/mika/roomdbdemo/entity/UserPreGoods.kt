package mika.roomdbdemo.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserPreGoods(
        @Embedded val user: User,
        @Relation(
                parentColumn = "preference_type",
                entityColumn = "type"
        ) val goodsList: List<Goods>,
        val remarks: String? = null
) {
}