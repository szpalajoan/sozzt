package pl.jkap.sozzt.landextracts.domain

import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.landextracts.dto.LandExtractsDto
import pl.jkap.sozzt.sample.SampleModifier

trait LandExtractsSample implements ContractSample {

    LandExtractsDto KRYNICA_LAND_EXTRACTS = LandExtractsDto.builder()
            .landExtractsId(KRYNICA_CONTRACT.contractId)
            .requestForPlotExtractsSent(false)
            .completed(false)
            .build()

    LandExtractsDto COMPLETED_KRYNICA_LAND_EXTRACTS = LandExtractsDto.builder()
            .landExtractsId(KRYNICA_CONTRACT.contractId)
            .requestForPlotExtractsSent(true)
            .completed(true)
            .build()

    LandExtractsDto with(LandExtractsDto landExtractsDto, Map<String, Object> properties) {
        return SampleModifier.with(LandExtractsDto.class, landExtractsDto, properties)
    }
} 