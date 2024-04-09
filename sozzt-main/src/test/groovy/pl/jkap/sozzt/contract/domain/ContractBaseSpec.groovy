package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.instant.InstantProvider
import pl.jkap.sozzt.instant.InstantSamples
import pl.jkap.sozzt.user.UserSample
import spock.lang.Specification

class ContractBaseSpec extends Specification implements ContractSample, UserSample, InstantSamples {
    InstantProvider instantProvider = new InstantProvider()
    ContractFacade contractFacade = new ContractConfiguration().contractFacade(instantProvider)

    def setup() {
        instantProvider.useFixedClock(NOW)
    }
}
