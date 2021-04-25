package mika.roomdbdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mika.roomdbdemo.entity.Goods
import kotlinx.android.synthetic.main.item_goods_list.view.*
import mika.roomdbdemo.R

class GoodsListAdapter : ListAdapter<Goods, GoodsListAdapter.GoodsViewHolder>(GoodsDiffer()) {

    var mListener: OnItemClickedListener? = null

    fun setList(list: List<Goods>) {
        val diffResult = DiffUtil.calculateDiff(GoodsDifferUtil(currentList, list), true)
        diffResult.dispatchUpdatesTo(this)
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        return GoodsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_goods_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        val goods = getItem(position)

        holder.code.text = goods.code.toString()
        holder.name.text = "${goods.name} ${goods.spec}"
        holder.price.text = String.format("%.2f", goods.price)

        if (goods.unit.isNullOrEmpty()) {
            holder.unit.text = ""
        } else {
            holder.unit.text = " /${goods.unit}"
        }

        holder.itemView.setOnClickListener {
            mListener?.onItemClicked(goods)
        }
        holder.itemView.setOnLongClickListener {
            mListener?.onItemLongClicked(goods, holder.adapterPosition)
            return@setOnLongClickListener true
        }
    }

    class GoodsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val code: TextView = itemView.item_goods_list_code
        val name: TextView = itemView.item_goods_list_name
        val price: TextView = itemView.item_goods_list_prices
        val unit: TextView = itemView.item_goods_list_unit
    }

    class GoodsDiffer : DiffUtil.ItemCallback<Goods>() {
        override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.price == newItem.price &&
                    oldItem.spec == newItem.spec &&
                    oldItem.unit == newItem.unit &&
                    oldItem.type == newItem.type &&
                    oldItem.date == newItem.date
        }
    }

    class GoodsDifferUtil(private val oldList: List<Goods>,
                          private val newList: List<Goods>): DiffUtil.Callback(){

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            false

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            false

    }

    interface OnItemClickedListener {
        fun onItemClicked(goods: Goods)
        fun onItemLongClicked(goods: Goods, adapterPosition: Int)
    }
}