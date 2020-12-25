package mika.roomdbdemo.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mika.roomdbdemo.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun loadAllUser(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

}