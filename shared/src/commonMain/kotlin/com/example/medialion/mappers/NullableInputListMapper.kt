package com.example.medialion.mappers

// Nullable to Non-nullable
interface NullableInputListMapper<I, O>: Mapper<List<I>?, List<O>> {
    class Impl<I, O>(
        private val mapper: Mapper<I, O>
    ) : NullableInputListMapper<I, O> {
        override fun map(input: List<I>?): List<O> {
            return input?.map { mapper.map(it) }.orEmpty()
        }
    }
}