package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.filter.data.dto.IndustryResponseDto
import ru.practicum.android.diploma.filter.domain.models.Industry

object IndustryResponseMapper {
    fun map(industryResponseDto: IndustryResponseDto?): List<Industry> {
        if (industryResponseDto == null)
            return emptyList()

        val root = listOf(Mapper.map(industryResponseDto))
        val result = industryResponseDto.industries.map { Mapper.map(it) }


        return listOf(root, result).flatten()
    }
}
