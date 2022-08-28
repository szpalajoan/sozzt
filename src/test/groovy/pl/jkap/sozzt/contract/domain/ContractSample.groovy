package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.AddContractDto
import pl.jkap.sozzt.contract.dto.DataInputContractDto

trait ContractSample {

    ContractFacade contractFacade = new ContractConfiguration().contractFacade()

    AddContractDto NEW_MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT = createNewContractDto("2022VOLTAGE", "Tarnów", "Electro")
    DataInputContractDto CONTRACT_WITH_PRELIMINARY_MAP_TO_UPLOAD_STEP = addContractDtoWithPreliminaryMapToUploadStep("2022VOLTAGE", "Tarnów", "Electro")

    private AddContractDto createNewContractDto(String invoiceNumber, String location, String executive) {
        return AddContractDto.builder()
                .invoiceNumber(invoiceNumber)
                .location(location)
                .executive(executive)
                .build()
    }

    DataInputContractDto addContractDtoWithPreliminaryMapToUploadStep(String invoiceNumber, String location, String executive) {
        DataInputContractDto dataInputContractDto = contractFacade.addContract(createNewContractDto(invoiceNumber, location, executive))
        contractFacade.confirmScanUploaded(dataInputContractDto.contractDataDto.id)
        contractFacade.confirmStep(dataInputContractDto.contractDataDto.id)
        return dataInputContractDto
    }

}