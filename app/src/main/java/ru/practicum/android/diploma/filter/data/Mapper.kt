package ru.practicum.android.diploma.filter.data

import ru.practicum.android.diploma.filter.data.dto.IndustryDto
import ru.practicum.android.diploma.filter.domain.models.Industry

class Mapper {
    fun mapIndustry(industryDto: IndustryDto): Industry {
        return Industry(
            industryDto.id,
            industryDto.name
        )

    }
}
