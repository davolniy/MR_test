package com.mr.mr_test.extension

fun Int.doOnOverCome(countOfItems: Int, action:() -> Unit ) {
    if (this == 0 || countOfItems == 0) return
    val from = if (5F > countOfItems) countOfItems - 1F else 5F
    val overCome = (countOfItems * (1F - from / countOfItems)).toInt()
    if (this == overCome) action.invoke()
}