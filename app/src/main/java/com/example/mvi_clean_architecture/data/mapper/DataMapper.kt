package com.example.mvi_clean_architecture.data.mapper

interface DataMapper<E, D> {

    fun mapFromEntity(type: E): D
}
