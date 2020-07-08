package com.solabe.paydaybank.ui.screens.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solabe.paydaybank.R
import com.solabe.paydaybank.ui.models.ExpenseCategory
import kotlinx.android.synthetic.main.expense_category_item.view.*

class CategoriesAdapter(private val categories: MutableList<ExpenseCategory>) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(LayoutInflater.from(parent.context)
                                                  .inflate(R.layout.expense_category_item,
                                                           parent,
                                                        false))
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    fun setItems(items: List<ExpenseCategory>) {
        categories.clear()
        categories.addAll(items)
        notifyDataSetChanged()
    }

    inner class CategoriesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(category: ExpenseCategory) {
            view.category_tv.text = category.category
            view.spent_tv.text = category.spent.toString()
            view.earned_tv.text = category.earned.toString()
            view.total_tv.text = category.total.toString()
        }
    }
}