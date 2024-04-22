package pl.jkap.sozzt.sample

import pl.jkap.sozzt.contract.domain.ContractDetailsSample
import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.contract.domain.LocationSample
import pl.jkap.sozzt.filestorage.domain.FileSample
import pl.jkap.sozzt.instant.InstantProvider
import pl.jkap.sozzt.instant.InstantSamples
import pl.jkap.sozzt.user.UserSample
import spock.lang.Specification

class SampleSpecification extends Specification implements FileSample, ContractSample, LocationSample,
        ContractDetailsSample, UserSample, InstantSamples {
    InstantProvider instantProvider = new InstantProvider()

    def setup() {
        instantProvider.useFixedClock(NOW)
    }
}
