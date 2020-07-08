package com.solabe.paydaybank.ui.models

data class ExpenseCategory(val category: String,
                           var spent: Double,
                           var earned: Double,
                           var total: Double) {
}