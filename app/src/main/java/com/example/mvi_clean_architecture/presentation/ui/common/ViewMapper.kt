package com.example.mvi_clean_architecture.presentation.ui.common

interface ViewMapper<out V, in D> {

    fun mapToView(type: D): V
}
