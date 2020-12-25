package mika.roomdbdemo.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import mika.roomdbdemo.entity.Goods
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface GoodsDao {

    @Query("SELECT * FROM goods")
    fun loadAllGoods(): List<Goods>

    @Query("SELECT * FROM goods")
    fun loadAllGoodsLiveData(): LiveData<List<Goods>>

    @Query("SELECT * FROM goods")
    fun loadAllGoodsSuspend(): Flow<List<Goods>>

    @Query("SELECT * FROM goods WHERE gid = :id")
    fun loadGoods(id: Int): Goods

    /**
     * @return 获得操作行数的返回值表示操作成功
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoods(goods: Goods): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoodsSuspend(goods: Goods): Long

    /**
     * @return 获得操作行数的返回值表示成功
     */
    @Update
    fun updateGoods(goods: Goods): Int

    /**
     * @return 获得删除行数的返回值表示成功
     */
    @Delete
    fun deleteGoods(goods: Goods): Int

    /**
     * 模糊查找对应code数据
     */
    @Query("SELECT * FROM goods WHERE code LIKE '%'||:code||'%'")
    fun searchGoods(code: Long): List<Goods>

}

