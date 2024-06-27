package com.example.mvi_clean_architecture.network.mappers

interface EntityMapper<M, E> {
    fun mapFromModel(model: M): E
}
