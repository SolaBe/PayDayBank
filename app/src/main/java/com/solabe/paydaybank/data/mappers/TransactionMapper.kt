package com.solabe.paydaybank.data.mappers

import com.solabe.paydaybank.data.models.remote.TransactionApi
import com.solabe.paydaybank.ui.models.ExpenseCategory
import com.solabe.paydaybank.ui.models.Transaction

object TransactionMapper {

    fun map(remoteTransactions: List<TransactionApi>) : List<ExpenseCategory> {
        val map = mutableMapOf<String, ExpenseCategory>()
        remoteTransactions.forEach {
            if (map.isEmpty() || !map.containsKey(it.category)) {
                map[it.category] = ExpenseCategory(it.category,
                                    if (it.amount.toDouble() > 0.0) 0.0 else it.amount.toDouble(),
                                    if (it.amount.toDouble() <= 0.0) 0.0 else it.amount.toDouble(),
                                    it.amount.toDouble())
            } else {
                if (map.containsKey(it.category)) {
                    val expenseCategory = map[it.category]
                    if (it.amount.toDouble() > 0.0) {
                        expenseCategory?.earned =+ it.amount.toDouble()
                    } else {
                        expenseCategory?.spent =+ it.amount.toDouble()
                    }
                    expenseCategory?.total =+ it.amount.toDouble()
                    map[it.category] = expenseCategory!!
                }
            }
        }
        return map.values.toList()
    }


    fun map(remote: TransactionApi)
            = Transaction(date = remote.date,
                          category = remote.category,
                          vendor = remote.vendor,
                          amount = remote.amount.toString())
}