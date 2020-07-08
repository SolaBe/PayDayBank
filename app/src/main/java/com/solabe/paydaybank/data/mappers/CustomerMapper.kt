package com.solabe.paydaybank.data.mappers

import com.solabe.paydaybank.data.models.local.Customer
import com.solabe.paydaybank.data.models.remote.CustomerApi
import com.solabe.paydaybank.data.models.remote.RegisterRequest

object CustomerMapper {

    fun map(remote: CustomerApi) =
        Customer(id = remote.id,
                firstName = remote.firstName,
                lastName = remote.lastName,
                email = remote.email,
                password = remote.password,
                phone = remote.phone,
                gender = remote.gender,
                dateOfBirth = remote.dateOfBirth)

    fun map(local: com.solabe.paydaybank.ui.models.Customer) =
            RegisterRequest(firstName = local.firstName,
                            lastName = local.lastName,
                            dateOfBirth = local.dateOfBirth,
                            email = local.email,
                            password = local.password,
                            phone = local.phone,
                            gender = local.gender)
}