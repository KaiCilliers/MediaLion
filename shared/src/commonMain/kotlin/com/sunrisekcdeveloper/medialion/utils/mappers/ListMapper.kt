package com.sunrisekcdeveloper.medialion.utils.mappers

// Non-nullable to Non-nullable
interface ListMapper<I, O>: Mapper<List<I>, List<O>> {
    class Impl<I, O>(
        private val mapper: Mapper<I, O>
    ) : ListMapper<I, O> {
        override fun map(input: List<I>): List<O> {
            return input.map { mapper.map(it) }
        }
    }
}