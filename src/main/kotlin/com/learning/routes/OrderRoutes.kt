package com.learning.routes

import com.learning.model.Order
import com.learning.model.orderStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*


/**
 * Similar to [registerCustomerRoutes], however, listing/registering more
 * due to separate functions here.
 */
fun Application.registerOrderRoutes() {
    routing {
        listOrdersRoute()
        postOrder()
        getOrder()
        getOrderTotal()
    }
}


/**
 * Unlike in [customerRouting], here each route is defined in separate functions.
 * As opposed to setting the base path, then adding/defining each method and is path.
 */
fun Route.listOrdersRoute() {
    get(ORDERS_PATH_BASE) {
        if (orderStorage.isNotEmpty()) {
            call.respond(orderStorage)
        }
    }
}

fun Route.getOrder() {
    get(ORDERS_PATH_BASE_ID) {
        val id = call.parameters["id"] ?: call.respondText(
            ID_MISSING_MALFORMED,
            status = HttpStatusCode.NotFound
        )

        val order = orderStorage.find { it.orderNumber == id } ?: call.respondText(
            ORDER_NOT_FOUND + id,
            status = HttpStatusCode.NotFound
        )

        call.respond(order)
    }
}

fun Route.postOrder() {
    post(ORDERS_PATH_BASE) {
        val order = call.receive<Order>()
        orderStorage.add(order)
        call.respondText(
            ORDER_CREATED,
            status = HttpStatusCode.Created
        )
    }
}

fun Route.getOrderTotal() {
    get(ORDERS_PATH_GET_TOTAL) {
        val id = call.parameters["id"] ?: return@get call.respondText(
            ID_MISSING_MALFORMED,
            status = HttpStatusCode.NotFound
        )

        val order = orderStorage.find { it.orderNumber == id } ?: return@get call.respondText(
            ORDER_NOT_FOUND,
            status = HttpStatusCode.NotFound
        )

        val orderTotal = order.orderedItems.sumOf { it.price * it.quantity }

        call.respond(orderTotal)
    }
}