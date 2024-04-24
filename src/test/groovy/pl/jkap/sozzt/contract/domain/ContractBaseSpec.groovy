package pl.jkap.sozzt.contract.domain


import pl.jkap.sozzt.sample.SampleSpecification

abstract class ContractBaseSpec extends SampleSpecification {
    ContractFacade contractFacade = new ContractConfiguration().contractFacade(instantProvider)

}
