package com.learning.routes

const val ORDERS_PATH_BASE = "/orders"
const val ORDERS_PATH_BASE_ID = "$ORDERS_PATH_BASE/{id}"
const val ORDERS_TOTAL = "/total"
const val ORDERS_PATH_GET_TOTAL = "$ORDERS_PATH_BASE_ID$ORDERS_TOTAL"