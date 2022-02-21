package com.learning.model

import kotlinx.serialization.Serializable

//storing all of our customers here for simplicity's sake.
val customerStorage = mutableListOf<Customer>()

@Serializable
data class Customer(val id: String, val firstName: String, val lastName: String, val email: String)
