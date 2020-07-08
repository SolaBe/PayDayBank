package com.solabe.paydaybank.data.mappers

import com.solabe.paydaybank.data.models.remote.AccountApi
import com.solabe.paydaybank.ui.models.Accounts
import com.solabe.paydaybank.utils.dateStringToSeconds

object AccountsMapper {

    fun map(accounts: List<AccountApi>)
            = Accounts(ids = accounts.map { it.id },
                       ealiestCreationDate = accounts.minBy { it.dateCreated.dateStringToSeconds() }?.dateCreated?.dateStringToSeconds())
}