package mika.roomdbdemo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import mika.roomdbdemo.AppExecutors
import mika.roomdbdemo.MyApplication
import mika.roomdbdemo.db.AppDataBase
import mika.roomdbdemo.entity.Goods
import kotlinx.coroutines.launch

class GoodsViewModel(private val database: AppDataBase) : ViewModel() {

    val mGoodsList: MutableLiveData<ArrayList<Goods>> = MutableLiveData()

    /**
     * 通过一个状态值来改版加载状态
     */
    val operateResult = MutableLiveData<Long>()

    /**
     * 从数据库加载商品列表
     */
    fun loadGoodsList() {
        AppExecutors.dbIO().execute {
            val goodsList = database.goodsDao().loadAllGoods()
            Log.d("mika", "db load goods finish $goodsList")
            mGoodsList.postValue(ArrayList(goodsList))
        }
    }

    /**
     * 根据code查找
     */
    fun searchGoods(code: Long) {
        operateResult.value = -1
        AppExecutors.dbIO().execute {
            val goodsList = database.goodsDao().searchGoods(code)
            Log.d("mika", "search goods finish $goodsList")
            mGoodsList.postValue(ArrayList(goodsList))
            operateResult.postValue(1)
        }
    }

    /**
     * 插入一条新商品
     */
    fun insert(goods: Goods) {
        operateResult.value = -1

        AppExecutors.dbIO().execute {
            val result = database.goodsDao().insertGoods(goods)
            Log.d("mika", "db insert ${goods.name}, result: ${result}")
            operateResult.postValue(result)
        }
    }

    fun insertSuspend(goods: Goods) {
        operateResult.value = -1
        viewModelScope.launch {
            val result = database.goodsDao().insertGoodsSuspend(goods)
            Log.d("mika", "db insert ${goods.name}, result: ${result}")
            operateResult.value = result
        }
    }

    fun update(goods: Goods) {
        operateResult.value = -1
        AppExecutors.dbIO().execute {
            val result = database.goodsDao().updateGoods(goods)
            Log.d("mika", "db update ${goods.id}, ${goods.name}, result: ${result}")
            operateResult.postValue(result.toLong())
        }
    }

    fun deleteGoods(goods: Goods, positon: Int) {
        AppExecutors.dbIO().execute {
            val result = database.goodsDao().deleteGoods(goods)
            Log.d("mika", "db delete ${goods.name}, result: ${result}, pos: ${positon}")
        }

        //移除对应Pos item
        mGoodsList.value?.removeAt(positon)
    }

    /**
     * ViewModel Factory
     */
    @Suppress("UNCHECKED_CAST")
    class Factory() : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val dataBase = MyApplication.instance.database
            return GoodsViewModel(dataBase) as T
        }
    }

}