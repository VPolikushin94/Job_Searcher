package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.filter.data.dto.IndustryResponseDto
import ru.practicum.android.diploma.filter.domain.models.Industry

class IndustryResponseMapper(
    private val mapper: Mapper
) {
    fun map(industryResponseDto: IndustryResponseDto?): List<Industry> {
        if (industryResponseDto == null) {
            return emptyList()
        }
        return industryResponseDto.industries.map { mapper.map(it) }
    }
}
