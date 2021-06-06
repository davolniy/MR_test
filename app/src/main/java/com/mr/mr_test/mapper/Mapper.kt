package com.mr.mr_test.mapper

abstract class Mapper<S, D> {
    abstract fun map(source: S): D

    fun map(source: List<S>): List<D> = source.map { map(it) }
}
