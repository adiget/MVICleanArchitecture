package com.example.mvi_clean_architecture.presentation.ui.prs.mapper

import com.example.mvi_clean_architecture.domain.model.DomainPrData
import com.example.mvi_clean_architecture.presentation.mapper.UserViewMapper
import com.example.mvi_clean_architecture.presentation.ui.common.ViewMapper
import com.example.mvi_clean_architecture.presentation.ui.prs.model.UiPrData
import com.example.mvi_clean_architecture.presentation.views.utils.DateTimeUtils
import javax.inject.Inject

class PrViewMapper @Inject constructor(private val userViewMapper: UserViewMapper) :
    ViewMapper<UiPrData, DomainPrData> {

    override fun mapToView(type: DomainPrData) =
        UiPrData(
            id = type.id,
            prTitle = type.title,
            prDesc = type.desc,
            user = userViewMapper.mapToView(type.user),
            closedAt = getValidDate(type.closedAt),
            createdAt = getValidDate(type.createdAt)
        )

    private fun getValidDate(date : String) : String {
        return if(date.isValid()){
            DateTimeUtils.getDayWithMonthName(date)
        } else
            "No date available."
    }

    }

fun String?.isValid(): Boolean {
    return !this.isNullOrEmpty() && this.isNotBlank()
}