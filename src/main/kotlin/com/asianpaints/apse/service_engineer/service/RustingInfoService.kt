package com.asianpaints.apse.service_engineer.service

import com.asianpaints.apse.service_engineer.constants.ToolTipCategoryEnum
import com.asianpaints.apse.service_engineer.domain.entity.ToolTipCategory
import com.asianpaints.apse.service_engineer.domain.entity.ToolTipInfo
import com.asianpaints.apse.service_engineer.dto.BlisteringInformationDTO
import com.asianpaints.apse.service_engineer.dto.CrackingGradingDistributionInformationDTO
import com.asianpaints.apse.service_engineer.dto.FlakingInformationDTO
import com.asianpaints.apse.service_engineer.dto.RustingDegreeDTO
import com.asianpaints.apse.service_engineer.repository.ToolTipCategoryRepository
import com.asianpaints.apse.service_engineer.repository.ToolTipInfoRepository
import org.springframework.stereotype.Service

@Service
class RustingInfoService(
    private val repository: ToolTipInfoRepository,
    private val toolTipCategoryRepository: ToolTipCategoryRepository
) {

    fun getAllCategory(): List<ToolTipCategory> = toolTipCategoryRepository.findAll()
    fun getAllByCategory(categoryName: String): List<Any> {
        val category = toolTipCategoryRepository.findByName(categoryName)
        return when (val categoryId = category.id) {
            ToolTipCategoryEnum.RUSTING_DEGREE -> repository.findAllByCategoryId(categoryId).map { it.toRustingDegreeDto() }
            ToolTipCategoryEnum.FLAKING_GRADING -> repository.findAllByCategoryId(categoryId).map { it.toFlakingInformationDto() }
            ToolTipCategoryEnum.BLISTERING -> repository.findAllByCategoryId(categoryId).map { it.toBlistering() }
            ToolTipCategoryEnum.CRACKING_GRADING_AND_DISTRIBUTION -> repository.findAllByCategoryId(categoryId).map { it.toCrackingGradingDistribution() }
            else -> throw IllegalArgumentException("Invalid category")
        }
    }

    private fun ToolTipInfo.toRustingDegreeDto() = RustingDegreeDTO(
        id, degreeOfRusting, rustedAreaPercentage, rating, sizeOfDefect
    )

    private fun ToolTipInfo.toFlakingInformationDto() = FlakingInformationDTO(
        id, flakedArea, sizeOfFlakedAreas
    )

    private fun ToolTipInfo.toBlistering() = BlisteringInformationDTO(
        id, rating, sizeOfDefect, quantityOfDefect
    )

    private fun ToolTipInfo.toCrackingGradingDistribution() = CrackingGradingDistributionInformationDTO(
        id, rating, sizeOfDefect, quantityOfDefect, areaPercentage, distribution
    )


}
/*

    fun createRustingDegree(dto: RustingDegreeDTO): RustingDegreeDTO = repository.save(dto.toEntity("Rusting Degree")).toRustingDegreeDto()

    fun createFlakingInformation(dto: FlakingInformationDTO): FlakingInformationDTO =
        repository.save(dto.toEntity("Flaking Information")).toFlakingInformationDto()


private fun RustingDegreeDTO.toEntity(category: String) = ToolTipInfo(
    category = category, degreeOfRusting = degreeOfRusting, rustedAreaPercentage = rustedAreaPercentage,
    rating = rating, sizeOfDefect = sizeOfDefect
)

private fun FlakingInformationDTO.toEntity(category: String) = ToolTipInfo(
    category = category, distribution = distribution, flakedArea = flakedArea, sizeOfFlakedAreas = sizeOfFlakedAreas
)
*/
