package pl.jkap.sozzt.remark.domain

import pl.jkap.sozzt.remark.dto.EditRemarkDto
import pl.jkap.sozzt.remark.dto.RemarkDto
import pl.jkap.sozzt.remark.exception.RemarkNotFoundException
import pl.jkap.sozzt.sample.SozztSpecification

import static RemarkType.*
import static pl.jkap.sozzt.sample.ExpectedStageSample.*

class RemarkSpec extends SozztSpecification {

    def "should add remark"() {
    given: "there is $KRYNICA_CONTRACT contract on $KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP stage"
        addKrynicaContractOnStage(BEGIN_DOCUMENTATION)
    and: "$MARCIN_TERRAIN_VISIONER is logged in"
        loginUser(MARCIN_TERRAIN_VISIONER)
    when: "$MARCIN_TERRAIN_VISIONER adds remark to $KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP"
        RemarkDto addedRemark = remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN))
    then: "remark is added"
        addedRemark == REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN
    }

    def "should mark remark as in progress"() {
    given: "there is $KRYNICA_CONTRACT contract on $KRYNICA_CONTRACT_TERRAIN_VISION_STEP stage added $TWO_WEEKS_AGO"
        instantProvider.useFixedClock(TWO_WEEKS_AGO)
        addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
    and: "$MARCIN_TERRAIN_VISIONER added remark to $KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP $WEEK_AGO"
        instantProvider.useFixedClock(WEEK_AGO)
        loginUser(MARCIN_TERRAIN_VISIONER)
        remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN))
    and: "$DAREK_PRELIMINARY_PLANER is logged in $NOW"
        instantProvider.useFixedClock(NOW)
        loginUser(DAREK_PRELIMINARY_PLANER)
    when: "$MARCIN_TERRAIN_VISIONER marks remark as in progress"
        RemarkDto remark = remarkFacade.markRemarkAsInProgress(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN.remarkId)
    then: "remark is marked as in progress"
        remark == with(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN, [remarkStatus: RemarkStatus.IN_PROGRESS,
                                                                       createdAt   : WEEK_AGO])
    }

    def "should not mark remark as in progress if remark does not exist"() {
        given: "there is $KRYNICA_CONTRACT contract on $KRYNICA_CONTRACT_TERRAIN_VISION_STEP stage added $TWO_WEEKS_AGO"
            instantProvider.useFixedClock(TWO_WEEKS_AGO)
            addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER is logged in $NOW"
            instantProvider.useFixedClock(NOW)
            loginUser(MARCIN_TERRAIN_VISIONER)
        when: "$MARCIN_TERRAIN_VISIONER marks remark as in progress"
            remarkFacade.markRemarkAsInProgress(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN.remarkId)
        then: "remark is marked as in progress"
            thrown(RemarkNotFoundException)
    }

    def "should mark remark as done"() {
    given: "there is $KRYNICA_CONTRACT contract on $KRYNICA_CONTRACT_TERRAIN_VISION_STEP stage added $TWO_WEEKS_AGO"
        instantProvider.useFixedClock(TWO_WEEKS_AGO)
        addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
    and: "$MARCIN_TERRAIN_VISIONER added remark to $KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP $WEEK_AGO"
        instantProvider.useFixedClock(WEEK_AGO)
        loginUser(MARCIN_TERRAIN_VISIONER)
        remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN))
    and: "$DAREK_PRELIMINARY_PLANER is logged in $NOW"
        instantProvider.useFixedClock(NOW)
        loginUser(DAREK_PRELIMINARY_PLANER)
    when: "$DAREK_PRELIMINARY_PLANER marks remark as done"
        RemarkDto remark = remarkFacade.markRemarkAsDone(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN.remarkId)
    then: "remark is marked as done"
        remark == with(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN, [remarkStatus: RemarkStatus.DONE,
                                                                       createdAt   : WEEK_AGO])
    }

    def "should not mark remark as done if remark does not exist"() {
        given: "there is $KRYNICA_CONTRACT contract on $KRYNICA_CONTRACT_TERRAIN_VISION_STEP stage added $TWO_WEEKS_AGO"
            instantProvider.useFixedClock(TWO_WEEKS_AGO)
            addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER added remark to $KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP $WEEK_AGO"
            instantProvider.useFixedClock(WEEK_AGO)
            loginUser(MARCIN_TERRAIN_VISIONER)
            remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN))
        and: "it's $NOW"
            instantProvider.useFixedClock(NOW)
        when: "$MARCIN_TERRAIN_VISIONER marks remark as done"
            remarkFacade.markRemarkAsDone(UUID.randomUUID())
        then: "remark is not marked as done"
            thrown(RemarkNotFoundException)
    }

    def "should cancel remark"() {
        given: "there is $KRYNICA_CONTRACT contract on $KRYNICA_CONTRACT_TERRAIN_VISION_STEP stage added $TWO_WEEKS_AGO"
            instantProvider.useFixedClock(TWO_WEEKS_AGO)
            addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER added remark to $KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP $WEEK_AGO"
            instantProvider.useFixedClock(WEEK_AGO)
            loginUser(MARCIN_TERRAIN_VISIONER)
            remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN))
        and: "it's $NOW"
            instantProvider.useFixedClock(NOW)
        when: "$MARCIN_TERRAIN_VISIONER cancels remark"
            RemarkDto remark = remarkFacade.cancelRemark(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN.remarkId)
        then: "remark is canceled"
            remark == with(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN, [remarkStatus: RemarkStatus.CANCELLED,
                                                                           createdAt   : WEEK_AGO])
    }

    def "should not cancel remark if remark does not exist"() {
        given: "there is $KRYNICA_CONTRACT contract on $KRYNICA_CONTRACT_TERRAIN_VISION_STEP stage added $TWO_WEEKS_AGO"
            instantProvider.useFixedClock(TWO_WEEKS_AGO)
            addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER added remark to $KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP $WEEK_AGO"
            instantProvider.useFixedClock(WEEK_AGO)
            loginUser(MARCIN_TERRAIN_VISIONER)
            remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN))
        and: "it's $NOW"
            instantProvider.useFixedClock(NOW)
        when: "$MARCIN_TERRAIN_VISIONER cancels remark"
            remarkFacade.cancelRemark(UUID.randomUUID())
        then: "remark is not canceled"
            thrown(RemarkNotFoundException)
    }

    def "should edit remark"() {
        given: "there is $KRYNICA_CONTRACT contract on $KRYNICA_CONTRACT_TERRAIN_VISION_STEP stage added $TWO_WEEKS_AGO"
            instantProvider.useFixedClock(TWO_WEEKS_AGO)
            addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER added remark to $KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP $WEEK_AGO"
            instantProvider.useFixedClock(WEEK_AGO)
            loginUser(MARCIN_TERRAIN_VISIONER)
            remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN))
        and: "it's $NOW"
            instantProvider.useFixedClock(NOW)
        when: "$MARCIN_TERRAIN_VISIONER edits remark"
            RemarkDto remark = remarkFacade.editRemark(EditRemarkDto.builder()
                    .remarkId(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN.remarkId)
                    .title("new title")
                    .description("new description")
                    .assignedTo(MONIKA_CONTRACT_INTRODUCER.name)
                    .deadline(TWO_WEEKS_AHEAD)
                    .build())
        then: "remark is edited"
            remark == with(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN, [title      : "new title",
                                                                           description: "new description",
                                                                           assignedTo : MONIKA_CONTRACT_INTRODUCER.name,
                                                                           deadline   : TWO_WEEKS_AHEAD,
                                                                           createdAt  : WEEK_AGO])
    }

    def "should not edit remark if remark does not exist"() {
        given: "there is $KRYNICA_CONTRACT contract on $KRYNICA_CONTRACT_TERRAIN_VISION_STEP stage added $TWO_WEEKS_AGO"
            instantProvider.useFixedClock(TWO_WEEKS_AGO)
            addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER added remark to $KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP $WEEK_AGO"
            instantProvider.useFixedClock(WEEK_AGO)
            loginUser(MARCIN_TERRAIN_VISIONER)
            remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN))
        and: "it's $NOW"
            instantProvider.useFixedClock(NOW)
        when: "$MARCIN_TERRAIN_VISIONER edits remark"
            remarkFacade.editRemark(EditRemarkDto.builder()
                    .remarkId(UUID.randomUUID())
                    .title("new title")
                    .description("new description")
                    .assignedTo(MONIKA_CONTRACT_INTRODUCER.name)
                    .deadline(TWO_WEEKS_AHEAD)
                    .build())
        then: "remark is not edited"
            thrown(RemarkNotFoundException)
    }

    def "should get remarks for contract step"() {
        given: "there is $KRYNICA_CONTRACT contract on $KRYNICA_CONTRACT_TERRAIN_VISION_STEP stage added $TWO_WEEKS_AGO"
            instantProvider.useFixedClock(TWO_WEEKS_AGO)
            addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER added remark to $KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP $WEEK_AGO"
            instantProvider.useFixedClock(WEEK_AGO)
            loginUser(MARCIN_TERRAIN_VISIONER)
            remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN))
        and: "$DANIEL_ROUTE_DRAWER added next remark to $KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP $YESTERDAY"
            instantProvider.useFixedClock(YESTERDAY)
            loginUser(DANIEL_ROUTE_DRAWER)
            remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_DANIEL))
        and: "$DANIEL_ROUTE_DRAWER added remark to $KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP $YESTERDAY"
            remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_ROUTE_PREPARATION_BY_DANIEL))
        when: "$MARCIN_TERRAIN_VISIONER gets remarks for $KRYNICA_CONTRACT contract and $PRELIMINARY_PLAN step"
            Collection<RemarkDto> remarks = remarkFacade.getRemarksForContract(KRYNICA_CONTRACT.contractId, PRELIMINARY_PLAN)
        then: "$MARCIN_TERRAIN_VISIONER gets $REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN and $REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_DANIEL remarks for $PRELIMINARY_PLAN sort by closest deadline"
            remarks == [with(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN, [createdAt: WEEK_AGO]), with(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_DANIEL, [createdAt: YESTERDAY])]
    }

    def "should not get remarks for contract step if remark does not exist"() {
        given: "there is $KRYNICA_CONTRACT contract on $KRYNICA_CONTRACT_TERRAIN_VISION_STEP stage added $TWO_WEEKS_AGO"
            instantProvider.useFixedClock(TWO_WEEKS_AGO)
            addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
        expect: "there are no remarks for $PRELIMINARY_PLAN step"
            remarkFacade.getRemarksForContract(KRYNICA_CONTRACT.contractId, PRELIMINARY_PLAN) == []
    }

    def "should get all remarks for contract"() {
        given: "there is $KRYNICA_CONTRACT contract on $KRYNICA_CONTRACT_TERRAIN_VISION_STEP stage added $TWO_WEEKS_AGO"
            instantProvider.useFixedClock(TWO_WEEKS_AGO)
            addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER added remark to $KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP $WEEK_AGO"
            instantProvider.useFixedClock(WEEK_AGO)
            loginUser(MARCIN_TERRAIN_VISIONER)
            remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN))
        and: "$DANIEL_ROUTE_DRAWER added next remark to $KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP $YESTERDAY"
            instantProvider.useFixedClock(YESTERDAY)
            loginUser(DANIEL_ROUTE_DRAWER)
            remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_DANIEL))
        and: "$DANIEL_ROUTE_DRAWER added remark to $KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP $NOW"
            instantProvider.useFixedClock(NOW)
            remarkFacade.addRemark(toAddRemarkDto(REMARK_FOR_KRYNICA_ROUTE_PREPARATION_BY_DANIEL))
        when: "$MARCIN_TERRAIN_VISIONER gets remarks for $KRYNICA_CONTRACT contract"
            Collection<RemarkDto> remarks = remarkFacade.getRemarksForContract(KRYNICA_CONTRACT.contractId)
        then: "$MARCIN_TERRAIN_VISIONER gets $REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN and $REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_DANIEL remarks for $PRELIMINARY_PLAN sort by closest deadline"
            remarks == [with(REMARK_FOR_KRYNICA_ROUTE_PREPARATION_BY_DANIEL, [deadline: TOMORROW]),
                        with(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_MARCIN, [createdAt: WEEK_AGO, deadline: WEEK_AHEAD]),
                        with(REMARK_FOR_KRYNICA_PRELIMINARY_PLAN_BY_DANIEL, [createdAt: YESTERDAY, deadline: TWO_WEEKS_AHEAD])]
    }

}
