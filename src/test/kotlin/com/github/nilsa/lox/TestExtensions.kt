package com.github.nilsa.lox

import org.junit.jupiter.api.Assertions

// isn't there a nice package for this??

fun <T> Iterable<T>.shouldBeEqualTo(that: Iterable<T>) {
    Assertions.assertIterableEquals(that, this)
}
fun <T> Iterable<T>.shouldBeEqualTo(vararg that: T) {
    this.shouldBeEqualTo(that.toList())
}