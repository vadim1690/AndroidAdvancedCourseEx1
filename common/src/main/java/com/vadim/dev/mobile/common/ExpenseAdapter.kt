package com.vadim.dev.mobile.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView

class ExpenseAdapter(
    var mDataSet: List<Expense>
) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {


    private var onItemClickListener: ((Expense) -> Unit)? = null

    fun setOnItemClickListener(listener: (Expense) -> Unit) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_expese,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.expense_item_name.text = mDataSet[position].name
        holder.expense_item_price.text = mDataSet[position].price.toString() + " $"
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(mDataSet[position]) }
        }
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expense_item_name = itemView.findViewById<MaterialTextView>(R.id.expense_item_name)
        val expense_item_price = itemView.findViewById<MaterialTextView>(R.id.expense_item_price)
        val add_btn = itemView.findViewById<ImageView>(R.id.add_btn)
    }

}