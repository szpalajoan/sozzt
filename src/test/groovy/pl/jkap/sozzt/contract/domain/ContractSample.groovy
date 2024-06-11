package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.sample.SampleModifier
import pl.jkap.sozzt.contract.dto.ContractDetailsDto
import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.contract.dto.CreateContractDto
import pl.jkap.sozzt.contract.dto.LocationDto
import pl.jkap.sozzt.instant.InstantSamples
import pl.jkap.sozzt.user.UserSample

trait ContractSample implements UserSample, LocationSample, ContractDetailsSample, InstantSamples {

    UUID FAKE_CONTRACT_ID = UUID.fromString("9ceccf5b-aaee-4d2c-86cb-d778624598fc")

    ContractDto KRYNICA_CONTRACT = createContractDto(
            UUID.fromString("21c4aaa0-4a11-4f83-aa2e-504e23d14495"),
            KRYNICA_CONTRACT_DETAILS,
            KRYNICA_LOCATION,
    )

    private ContractDto createContractDto(UUID id, ContractDetailsDto contractDetailsDto, LocationDto location) {
        return ContractDto.builder()
        .contractId(id)
        .contractDetails(contractDetailsDto)
        .location(location)
        .createdBy(MONIKA_CONTRACT_INTRODUCER.name)
        .createdAt(NOW)
        .isScanFromTauronUploaded(false)
        .build()
    }

    ContractDto with(ContractDto contractDto, Map<String, Object> properties) {
        return SampleModifier.with(ContractDto.class, contractDto, properties)
    }

    CreateContractDto toCreateContractDto(ContractDto contractDto) {
        return CreateContractDto.builder()
                .contractId(contractDto.contractId)
                .contractDetailsDto(contractDto.contractDetails)
                .location(contractDto.location)
                .build()
    }

}