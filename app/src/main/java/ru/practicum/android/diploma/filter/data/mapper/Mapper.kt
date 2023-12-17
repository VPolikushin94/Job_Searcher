package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.filter.data.dto.IndustryDto
import ru.practicum.android.diploma.filter.data.dto.IndustryResponseDto
import ru.practicum.android.diploma.filter.domain.models.Industry

object Mapper {
    fun map(industryDto: IndustryDto): Industry {
        return Industry(
            id = industryDto.id,
            name = industryDto.name
        )
    }

    fun map(industryResponseDto: IndustryResponseDto): Industry {
        return Industry(
            id = industryResponseDto.id,
            name = industryResponseDto.name
        )
    }
}
