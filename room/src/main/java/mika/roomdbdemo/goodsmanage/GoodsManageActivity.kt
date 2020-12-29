package mika.roomdbdemo.goodsmanage

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import mika.roomdbdemo.entity.Goods
import mika.roomdbdemo.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.activity_goods_manage.*
import mika.roomdbdemo.R
import java.util.*

const val GOODS_MANAGE_CREATE = 0

const val GOODS_MANAGE_DELETE = 1

/**
 * 商品 添加/修改
 */
class GoodsManageActivity : AppCompatActivity() {

    /**
     * 0-create, 1-update
     */
    private var manageType = GOODS_MANAGE_CREATE

    private var goodsId: Int? = null

    private var goodsViewModel: GoodsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_manage)

        init()
        setDataObserver()
    }

    private fun init() {
        val goods = intent.getParcelableExtra<Goods>("goods")
        manageType = if (goods != null) GOODS_MANAGE_DELETE else GOODS_MANAGE_CREATE
        if (manageType == GOODS_MANAGE_DELETE) {
            goodsId = goods?.id
            setInputData(goods!!)
        }

        goodsViewModel = GoodsViewModel.Factory().create(GoodsViewModel::class.java)
    }

    private fun setInputData(goods: Goods) {
        goods_manage_code.setText(goods.code.toString())
        goods_manage_name.setText(goods.name)
        goods_manage_spec.setText(goods.spec ?: "")
        goods_manage_type.setText(goods.type ?: "")
        goods_manage_unit.setText(goods.unit ?: "")
        goods_manage_price.setText(goods.price.toString())
    }

    private fun setDataObserver() {
        goodsViewModel?.operateResult?.observe(this, Observer {
            val isLoading = it == -1L
            goods_manage_progress.visibility = if (isLoading) View.VISIBLE else View.GONE
            goods_manage_commit.isEnabled = !isLoading
            if (!isLoading) {
                val msg = if (manageType == GOODS_MANAGE_DELETE) "更新完成" else "添加完成"
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                //清除输入
                clearInputData()
            }
        })
    }

    fun commitGoods(v: View) {
        val code = goods_manage_code.text?.toString()?.run {
            if (isNullOrEmpty()) {
                toast("code")
                return
            }
            toLong()
        }

        val name = goods_manage_name.text?.toString()?.apply {
            if (isNullOrEmpty()) {
                toast("商品名")
                return
            }
        }

        val price = goods_manage_price.text?.toString()?.run {
            if (isNullOrEmpty()) {
                toast("价格")
                return
            }
            toFloat()
        }

        val spec = goods_manage_spec.text?.toString()
        val unit = goods_manage_unit.text?.toString()
        val type = goods_manage_type.text?.toString()

        val goods = Goods(
            goodsId, code!!, name!!, spec, unit, type, price!!, System.currentTimeMillis()
        )

        if (manageType == GOODS_MANAGE_DELETE) {
            goodsViewModel?.update(goods)
        } else {
            goodsViewModel?.insertSuspend(goods)
        }

        setResult(Activity.RESULT_OK)
    }

    private fun clearInputData() {
        goods_manage_code.editableText.clear()
        goods_manage_name.editableText.clear()
        goods_manage_spec.editableText.clear()
        goods_manage_unit.editableText.clear()
        goods_manage_type.editableText.clear()
        goods_manage_price.editableText.clear()
    }

    private fun toast(s: String) {
        val msg = String.format("%s输入异常", s)
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        finish()
    }

}