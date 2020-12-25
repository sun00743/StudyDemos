package mika.roomdbdemo.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import mika.roomdbdemo.db.dao.GoodsDao
import mika.roomdbdemo.db.dao.UserDao
import mika.roomdbdemo.entity.Goods
import mika.roomdbdemo.entity.User

/**
 * 应用 RoomDataBase，使用注解声明表和version
 */
@Database(entities = [
    Goods::class,
    User::class],
    version = 3)
//@TypeConverters(DateTypeConverters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun goodsDao(): GoodsDao
    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        @JvmStatic
        fun getInstance(context: Context): AppDataBase =
            INSTANCE ?: synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = createDataBase(context)
                    //todo
                }
                INSTANCE!!
            }

        private fun createDataBase(context: Context): AppDataBase =
            Room.databaseBuilder(context, AppDataBase::class.java, "demo_db")
                //创建
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //todo
                        Log.d("mika", "db ${db.toString()} is created")
                    }
                })
                .build()

        /**
         * 版本升级 v1 -> v2
         */
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `user` (`id` INTEGER, `name` TEXT NOT NULL, PRIMARY KEY(`id`))")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE goods ADD COLUMN joined_date_millis INTEGER")
            }
        }

    }

}