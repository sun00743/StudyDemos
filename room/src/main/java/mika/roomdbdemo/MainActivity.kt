package mika.roomdbdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hd123.roomdbdemo.R
import mika.roomdbdemo.adapter.GoodsListAdapter
import mika.roomdbdemo.entity.Goods
import mika.roomdbdemo.goodsmanage.GoodsManageActivity
import mika.roomdbdemo.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主界面，商品展示，查询
 */
class MainActivity : AppCompatActivity(), GoodsListAdapter.OnItemClickedListener {

    private val REQUEST_MANAGE_GOODS = 1

    private var goodsViewModel: GoodsViewModel? = null

    private var mAdapter = GoodsListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        setRecyclerView()
        setDataObserver()

        goodsViewModel?.loadGoodsList()
    }

    /**
     * 初始化
     */
    private fun init() {
        goodsViewModel = GoodsViewModel.Factory().create(GoodsViewModel::class.java)

//        AppExecutors.dbIO().execute {
//            val userDao = MyApplication.instance.database.userDao()
//            userDao.insert(User(name = "mika1"))
//            userDao.insert(User(name = "mika3"))
//
//            val loadAllUser = userDao.loadAllUser()
//
//            Log.d("mika", "all user list: $loadAllUser")
//        }
    }

    private fun setRecyclerView() {
        mAdapter.mListener = this
        goods_list_recycler.adapter = mAdapter
    }

    private fun setDataObserver() {
        goodsViewModel?.mGoodsList?.observe(this, Observer {
            goods_list_progress.visibility = View.GONE

            mAdapter.submitList(it)
        })

        //加载状态observer
        goodsViewModel?.operateResult?.observe(this, Observer {
            val isLoading = it == -1L
            goods_list_progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }

    override fun onItemClicked(goods: Goods) {
        val intent = Intent(this, GoodsManageActivity::class.java)
        intent.putExtra("goods", goods)
        startActivityForResult(intent, REQUEST_MANAGE_GOODS)
    }

    /**
     * 长按弹窗删除
     */
    override fun onItemLongClicked(goods: Goods, adapterPosition: Int) {
        MaterialAlertDialogBuilder(this)
            .setMessage("delete goods ?")
            .setPositiveButton("yes") { dialog, which ->
                goodsViewModel?.deleteGoods(goods, adapterPosition)
                mAdapter.notifyItemRemoved(adapterPosition)
            }
            .setNegativeButton("no") { dialog, which ->
                dialog.dismiss()
            }
            .create().show()
    }

    /**
     * 根据code模糊搜索
     */
    fun searchGoods(v: View) {
        val str = goods_list_search_input.text?.toString()
        val code = if (str.isNullOrEmpty()) -1 else str.toLong()

        if (code == -1L) {
            goodsViewModel?.loadGoodsList()
        } else {
            goodsViewModel?.searchGoods(code)
        }
    }

    /**
     * 跳转添加商品界面
     */
    fun goAddGoods(v: View) {
        startActivityForResult(Intent(this, GoodsManageActivity::class.java), REQUEST_MANAGE_GOODS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            goodsViewModel?.loadGoodsList()
        }
    }

}