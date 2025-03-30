package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.contract.dto.CreateContractDto
import pl.jkap.sozzt.instant.InstantSamples
import pl.jkap.sozzt.sample.SampleModifier
import pl.jkap.sozzt.user.UserSample

import static pl.jkap.sozzt.contract.dto.ContractStepDto.ContractStepStatusDto.DONE
import static pl.jkap.sozzt.contract.dto.ContractStepDto.ContractStepStatusDto.IN_PROGRESS
import static pl.jkap.sozzt.contract.dto.ContractStepDto.ContractStepStatusDto.NOT_ACTIVE
import static pl.jkap.sozzt.contract.dto.ContractStepDto.ContractStepStatusDto.ON_HOLD

trait ContractSample implements UserSample, LocationSample, ContractDetailsSample, ContractStepSample, InstantSamples {

    UUID FAKE_CONTRACT_ID = UUID.fromString("9ceccf5b-aaee-4d2c-86cb-d778624598fc")

    ContractDto KRYNICA_CONTRACT = with(new ContractDto(), [
        contractId: UUID.fromString("21c4aaa0-4a11-4f83-aa2e-504e23d14495"),
        contractDetails: KRYNICA_CONTRACT_DETAILS,
        location: KRYNICA_LOCATION,
        createdBy: MONIKA_CONTRACT_INTRODUCER.name,
        createdAt: NOW,
        isScanFromTauronUploaded: false,
        contractSteps: [],
        zudConsentRequired: true
    ])

    ContractDto INTRODUCED_KRYNICA_CONTRACT = with(KRYNICA_CONTRACT, [isScanFromTauronUploaded: true,
                                                                      contractSteps           : [KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP,
                                                                                                 KRYNICA_CONTRACT_TERRAIN_VISION_STEP,
                                                                                                 KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP,
                                                                                                 KRYNICA_ROUTE_PREPARATION_STEP,
                                                                                                 KRYNICA_LAND_EXTRACTS_STEP,
                                                                                                 KRYNICA_CONSENTS_COLLECTION_STEP,
                                                                                                 KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP]])

    ContractDto COMPLETED_PRELIMINARY_PLAN_KRYNICA_CONTRACT = with(INTRODUCED_KRYNICA_CONTRACT, [
            contractSteps: [with(KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONTRACT_TERRAIN_VISION_STEP, [contractStepStatus: IN_PROGRESS]),
                            with(KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP, [contractStepStatus: NOT_ACTIVE]),
                            with(KRYNICA_ROUTE_PREPARATION_STEP, [contractStepStatus: ON_HOLD]),
                            with(KRYNICA_LAND_EXTRACTS_STEP, [contractStepStatus: ON_HOLD]),
                            with(KRYNICA_CONSENTS_COLLECTION_STEP, [contractStepStatus: ON_HOLD]),
                            with(KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP, [contractStepStatus: ON_HOLD])]])

    ContractDto COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT = with(COMPLETED_PRELIMINARY_PLAN_KRYNICA_CONTRACT, [
            contractSteps: [with(KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONTRACT_TERRAIN_VISION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP, [contractStepStatus: IN_PROGRESS]),
                            with(KRYNICA_ROUTE_PREPARATION_STEP, [contractStepStatus: ON_HOLD]),
                            with(KRYNICA_LAND_EXTRACTS_STEP, [contractStepStatus: IN_PROGRESS]),
                            with(KRYNICA_CONSENTS_COLLECTION_STEP, [contractStepStatus: ON_HOLD]),
                            with(KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP, [contractStepStatus: ON_HOLD])]])

    ContractDto COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT_WITH_NOT_NECESSARY_ROUTE_PREPARATION_STEP = with(COMPLETED_PRELIMINARY_PLAN_KRYNICA_CONTRACT, [
            contractSteps: [with(KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONTRACT_TERRAIN_VISION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP, [contractStepStatus: NOT_ACTIVE]),
                            with(KRYNICA_ROUTE_PREPARATION_STEP, [contractStepStatus: IN_PROGRESS]),
                            with(KRYNICA_LAND_EXTRACTS_STEP, [contractStepStatus: IN_PROGRESS]),
                            with(KRYNICA_CONSENTS_COLLECTION_STEP, [contractStepStatus: ON_HOLD]),
                            with(KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP, [contractStepStatus: ON_HOLD])]])

    ContractDto COMPLETED_PROJECT_PURPOSES_MAP_PREPARATION_KRYNICA_CONTRACT = with(COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT, [
            contractSteps: [with(KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONTRACT_TERRAIN_VISION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_ROUTE_PREPARATION_STEP, [contractStepStatus: IN_PROGRESS]),
                            with(KRYNICA_LAND_EXTRACTS_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONSENTS_COLLECTION_STEP, [contractStepStatus: ON_HOLD]),
                            with(KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP, [contractStepStatus: ON_HOLD])]])


    ContractDto COMPLETED_ROUTE_PREPARATION_KRYNICA_CONTRACT = with(COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT, [
            contractSteps: [with(KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONTRACT_TERRAIN_VISION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_ROUTE_PREPARATION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_LAND_EXTRACTS_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONSENTS_COLLECTION_STEP, [contractStepStatus: IN_PROGRESS]),
                            with(KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP, [contractStepStatus: ON_HOLD])]])

    ContractDto COMPLETED_ROUTE_PREPARATION_KRYNICA_CONTRACT_WITH_COMPLETED_CONSENTS = with(COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT, [
            contractSteps: [with(KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONTRACT_TERRAIN_VISION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_ROUTE_PREPARATION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_LAND_EXTRACTS_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONSENTS_COLLECTION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP, [contractStepStatus: IN_PROGRESS])]])

    ContractDto COMPLETED_CONSENTS_PREPARATION_KRYNICA_CONTRACT = with(COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT, [
            contractSteps: [with(KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONTRACT_TERRAIN_VISION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_ROUTE_PREPARATION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_LAND_EXTRACTS_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONSENTS_COLLECTION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP, [contractStepStatus: IN_PROGRESS])]])

    ContractDto COMPLETED_CONSENTS_PREPARATION_KRYNICA_CONTRACT_WITH_NOT_COMPLETED_ROUTE_PREPARATION_STEP = with(COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT, [
            contractSteps: [with(KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONTRACT_TERRAIN_VISION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP, [contractStepStatus: IN_PROGRESS]),
                            with(KRYNICA_CONSENTS_COLLECTION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP, [contractStepStatus: ON_HOLD])]])

    ContractDto COMPLETED_CONSENTS_PREPARATION_KRYNICA_CONTRACT_WITH_NOT_NECESSARY_PROJECT_PURPOSES_MAP_PREPARATION  = with(COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT, [
            contractSteps: [with(KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONTRACT_TERRAIN_VISION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP, [contractStepStatus: NOT_ACTIVE]),
                            with(KRYNICA_ROUTE_PREPARATION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONSENTS_COLLECTION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP, [contractStepStatus: IN_PROGRESS])]])

    ContractDto COMPLETED_PREPARATION_OF_DOCUMENTATION_STEP = with(COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT, [
            contractSteps: [with(KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONTRACT_TERRAIN_VISION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_ROUTE_PREPARATION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_LAND_EXTRACTS_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_CONSENTS_COLLECTION_STEP, [contractStepStatus: DONE]),
                            with(KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP, [contractStepStatus: DONE])]])

    ContractDto with(ContractDto contractDto, Map<String, Object> properties) {
        return SampleModifier.with(ContractDto.class, contractDto, properties)
    }

    CreateContractDto toCreateContractDto(ContractDto contractDto) {
        return CreateContractDto.builder()
                .contractId(contractDto.contractId)
                .contractDetailsDto(contractDto.contractDetails)
                .location(contractDto.location)
                .zudConsentRequired(contractDto.isZudConsentRequired())
                .build()
    }

}