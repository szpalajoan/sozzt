package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.instant.InstantProvider
import pl.jkap.sozzt.instant.InstantSamples
import pl.jkap.sozzt.sample.SampleSpecification
import pl.jkap.sozzt.user.UserSample
import spock.lang.Specification

class ContractBaseSpec extends SampleSpecification {
    InstantProvider instantProvider = new InstantProvider()
    ContractFacade contractFacade = new ContractConfiguration().contractFacade(instantProvider)

    def setup() {
        instantProvider.useFixedClock(NOW)
    }
}
