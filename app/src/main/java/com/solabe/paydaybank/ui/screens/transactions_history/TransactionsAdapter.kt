package com.solabe.paydaybank.ui.screens.transactions_history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solabe.paydaybank.R
import com.solabe.paydaybank.ui.models.Transaction
import kotlinx.android.synthetic.main.transaction_item.view.*

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>()  {

    private val transactionItems = mutableListOf<Transaction>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false))
    }

    override fun getItemCount() = transactionItems.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactionItems[position])
    }

    fun addItems(items: List<Transaction>) {
        transactionItems.addAll(items)
        notifyItemRangeChanged(transactionItems.size - items.size, items.size)
    }

    inner class TransactionViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(transaction: Transaction) {
            view.date_tv.text = String.format(view.context.getString(R.string.date_text),
                                    transaction.date)
            view.category_tv.text = String.format(view.context.getString(R.string.category_text),
                                    transaction.category)
            view.amount_tv.text = String.format(view.context.getString(R.string.amount_text),
                                    transaction.amount)
            view.vendor_tv.text = String.format(view.context.getString(R.string.vendor_text),
                                    transaction.vendor)
        }
    }
}