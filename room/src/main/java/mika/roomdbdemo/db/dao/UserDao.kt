package mika.roomdbdemo.db.dao

import androidx.room.*
import mika.roomdbdemo.entity.User
import mika.roomdbdemo.entity.UserPreGoods

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun loadAllUser(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Transaction
    @Query("SELECT * FROM user")
    fun loadUserPreGoods(): List<UserPreGoods>

}