package pl.jkap.sozzt.consent.domain

import pl.jkap.sozzt.consent.dto.AddConsentDto

trait ConsentSample {

    ConsentFacade consentFacade = new ConsentConfiguration().consentFacade()

    AddConsentDto NEW_CONSENT_IN_TARNOW_TO_ACCEPT = createNewConsentDto(UUID.randomUUID(), "Tarnów", "Tanrów, ul. Krakowska 33")

    private AddConsentDto createNewConsentDto(UUID idContract, String location, String contact) {

        return AddConsentDto.builder()
                .idContract(idContract)
                .location(location).contact(contact)
                .build()
    }


}