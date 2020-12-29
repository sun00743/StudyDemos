package mika.roomdbdemo.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//todo 定义对象之间的关系
@Parcelize
@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Int? = null,
    val name: String,
    @ColumnInfo(name = "preference_type")
    val preferenceType: String? = null
) : Parcelable