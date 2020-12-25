package mika.roomdbdemo.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "goods")
data class Goods(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "gid") val id: Int? = null,

    /**
     * 条码
     */
    val code: Long,
    val name: String,

    /**
     * 商品规格
     */
    val spec: String? = "",

    /**
     * 数量单位
     */
    @ColumnInfo(name = "munit") val unit: String? = "未知",

    /**
     * 商品分类
     */
    val type: String? = "未知",
    val price: Float,

    /**
     * 添加日期
     * v3版本加入
     */
    @ColumnInfo(name = "joined_date_millis") val date: Long?
//    , @ColumnInfo(name = "joined_date") val date: OffsetDateTime? = null
) : Parcelable {
}