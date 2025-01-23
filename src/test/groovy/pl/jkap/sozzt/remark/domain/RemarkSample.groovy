package pl.jkap.sozzt.remark.domain

import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.contract.domain.ContractStepSample
import pl.jkap.sozzt.instant.InstantSamples
import pl.jkap.sozzt.remark.dto.AddRemarkDto
import pl.jkap.sozzt.remark.dto.EditRemarkDto
import pl.jkap.sozzt.remark.dto.RemarkDto
import pl.jkap.sozzt.sample.SampleModifier
import pl.jkap.sozzt.user.UserSample

trait RemarkSample implements ContractStepSample, ContractSample, UserSample, InstantSamples {

    RemarkDto REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN = RemarkDto.builder()
            .remarkId(UUID.fromString("ed5cc224-a85c-4673-b106-ceeae512a83d"))
            .contractId(KRYNICA_CONTRACT.contractId)
            .remarkContractStep(RemarkContractStep.PRELIMINARY_PLAN)
            .remarkStatus(RemarkStatus.IN_PROGRESS)
            .title("Inspect Wiring")
            .description("Check all wiring for safety compliance.")
            .createdBy(MARCIN_TERRAIN_VISIONER.name)
            .createdAt(NOW)
            .assignedTo(DAREK_PRELIMINARY_PLANER.name)
            .deadline(WEEK_AHEAD)
            .remarkStatus(RemarkStatus.NEW)
            .build()

    RemarkDto REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_DANIEL = RemarkDto.builder()
            .remarkId(UUID.fromString("723d0542-49bd-4c05-b629-b3c39cb30bbf"))
            .contractId(KRYNICA_CONTRACT.contractId)
            .remarkContractStep(RemarkContractStep.PRELIMINARY_PLAN)
            .remarkStatus(RemarkStatus.IN_PROGRESS)
            .title("Review Circuit Layout")
            .description("Ensure the circuit layout meets the project specifications.")
            .createdBy(DANIEL_ROUTE_DRAWER.name)
            .createdAt(NOW)
            .assignedTo(DAREK_PRELIMINARY_PLANER.name)
            .deadline(TWO_WEEKS_AHEAD)
            .remarkStatus(RemarkStatus.NEW)
            .build()

    RemarkDto REMARK_FOR_KRYNICA_ROUTE_PREPARATION_BY_DANIEL = RemarkDto.builder()
            .remarkId(UUID.randomUUID())
            .contractId(KRYNICA_CONTRACT.contractId)
            .remarkContractStep(RemarkContractStep.ROUTE_PREPARATION)
            .remarkStatus(RemarkStatus.IN_PROGRESS)
            .title("Boundary marking of plot 145/7")
            .description("Correct the boundary for plot 145/7.")
            .createdBy(DANIEL_ROUTE_DRAWER.name)
            .createdAt(NOW)
            .assignedTo(WALDEK_SURVEYOR.name)
            .deadline(WEEK_AHEAD)
            .remarkStatus(RemarkStatus.NEW)
            .build()

    AddRemarkDto toAddRemarkDto(RemarkDto remarkDto) {
        return AddRemarkDto.builder()
                .remarkId(remarkDto.remarkId)
                .contractId(remarkDto.contractId)
                .remarkContractStep(remarkDto.remarkContractStep)
                .title(remarkDto.title)
                .description(remarkDto.description)
                .assignedTo(remarkDto.assignedTo)
                .deadline(remarkDto.deadline)
                .build()
    }

    RemarkDto with(RemarkDto remarkDto, Map<String, Object> properties) {
        return SampleModifier.with(RemarkDto.class, remarkDto, properties)
    }
}