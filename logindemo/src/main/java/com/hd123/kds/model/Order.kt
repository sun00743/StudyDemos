package com.hd123.kds.model

/**
 * @param code  取餐号
 */
data class Order(
        val uuid: String,
        val code: String,
        val from: Int,
        val orderDate: String,
        val cookState: Int = STATE_COOKING
) {
    companion object {
        const val FROM_ONLINE = 0
        const val FROM_STORE = 1

        const val STATE_COOKING = 0
        const val STATE_READY = 1
        const val STATE_FINISH = 2
    }
}
