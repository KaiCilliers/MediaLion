package com.sunrisekcdeveloper.medialion.utils.mappers

// Non-nullable to Nullable
interface NullableOutputListMapper<I, O>: Mapper<List<I>, List<O>?> {
    class Impl<I, O>(
        private val mapper: Mapper<I, O>
    ) : NullableOutputListMapper<I, O> {
        override fun map(input: List<I>): List<O>? {
            return if (input.isEmpty()) null else input.map { mapper.map(it) }
        }
    }
}