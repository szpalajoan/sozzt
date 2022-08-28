package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.AddContractDto
import pl.jkap.sozzt.contract.dto.ContractDataDto
import pl.jkap.sozzt.contract.dto.DataInputContractDto

trait ContractSample {

    ContractFacade contractFacade = new ContractConfiguration().contractFacade()

    AddContractDto NEW_MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT = createNewContractDto("2022VOLTAGE", "Tarnów", "Electro")
    ContractDataDto CONTRACT_WITH_PRELIMINARY_MAP_TO_UPLOAD_STEP = addContractDtoWithPreliminaryMapToUploadStep("2022VOLTAGE", "Tarnów", "Electro")
    ContractDataDto CONTRACT_WITH_PRELIMINARY_MAP_TO_VERIFY_STEP = addContractDtoWithPreliminaryMapToVerifyStep("2022VOLTAGE", "Tarnów", "Electro")
    ContractDataDto CONTRACT_WITH_LIST_OF_CONSENT_TO_ADD_STEP = addContractDtoWithListOfConsentsToAddStep("2022VOLTAGE", "Tarnów", "Electro")


    private AddContractDto createNewContractDto(String invoiceNumber, String location, String executive) {
        return AddContractDto.builder()
                .invoiceNumber(invoiceNumber)
                .location(location)
                .executive(executive)
                .build()
    }

    ContractDataDto addContractDtoWithPreliminaryMapToUploadStep(String invoiceNumber, String location, String executive) {
        DataInputContractDto dataInputContractDto = contractFacade.addContract(createNewContractDto(invoiceNumber, location, executive))
        contractFacade.confirmScanUploaded(dataInputContractDto.contractDataDto.id)
        return contractFacade.confirmStep(dataInputContractDto.contractDataDto.id)
    }

    ContractDataDto addContractDtoWithPreliminaryMapToVerifyStep(String invoiceNumber, String location, String executive) {
        ContractDataDto contractDataDto = addContractDtoWithPreliminaryMapToUploadStep(invoiceNumber, location, executive)
        contractFacade.confirmPreliminaryMapUploaded(contractDataDto.id)
        return contractFacade.confirmStep(contractDataDto.id)
    }

    ContractDataDto addContractDtoWithListOfConsentsToAddStep(String invoiceNumber, String location, String executive) {
        ContractDataDto contractDataDto = addContractDtoWithPreliminaryMapToVerifyStep(invoiceNumber, location, executive)
        return contractFacade.confirmStep(contractDataDto.id)
    }

}