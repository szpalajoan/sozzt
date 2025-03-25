package pl.jkap.sozzt.landextracts.domain;

import lombok.Value;

import java.util.UUID;

@Value
public class RequestForLandExtractsSentEvent {
    UUID landExtractsId;
} 