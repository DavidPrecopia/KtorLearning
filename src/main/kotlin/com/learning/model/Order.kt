package com.learning.model

import kotlinx.serialization.Serializable

//our Db for our orders.
val orderStorage = mutableListOf(
    Order("2022", listOf(
        OrderItem("6700 XT", 950.00, 1),
        OrderItem("5600x", 279.99, 1)
    )),
    Order("2020-04-06-01", listOf(
        OrderItem("ASUS ROG Desk Pad", 35.95, 1),
        OrderItem("Apple Pencil", 105.99, 2)
    )),
    Order("2020-04-03-01", listOf(
        OrderItem("Aquafina 32pk", 5.64, 10),
        OrderItem("Mint Ice Cream", 8.00, 2),
        OrderItem("Pizza 3pk", 12.90, 4)
    ))
)

@Serializable
data class Order(val orderNumber: String, val orderedItems: List<OrderItem>)

//due to this being referenced by the Order class, it needs to be serializable as well.
@Serializable
data class OrderItem(val name: String, val price: Double, val quantity: Int)
