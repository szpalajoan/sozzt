package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.AddContractDto
import pl.jkap.sozzt.contract.dto.ContractDto

trait ContractSample {

    ContractFacade contractFacade = new ContractConfiguration().contractFacade()

    AddContractDto NEW_MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT = createNewContractDto("2022VOLTAGE", "Tarnów", "Electro")
    ContractDto CONTRACT_WITH_PRELIMINARY_MAP_TO_UPLOAD_STEP = addContractDtoWithPreliminaryMapToUploadStep("2022VOLTAGE", "Tarnów", "Electro")

    private AddContractDto createNewContractDto(String invoiceNumber, String location, String executive) {
        return AddContractDto.builder()
                .invoiceNumber(invoiceNumber)
                .location(location)
                .executive(executive)
                .build()
    }

    ContractDto addContractDtoWithPreliminaryMapToUploadStep(String invoiceNumber, String location, String executive) {
        ContractDto contractDto = contractFacade.addContract(createNewContractDto(invoiceNumber, location, executive))
        contractFacade.confirmScanUploaded(contractDto.id)
        contractFacade.confirmStep(contractDto.id)
        return contractDto
    }

}