package com.cseiu.passnetorganizer.domain.compensating;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AddStudentCompensating extends BaseCompensating {
    String organizationId;
    String userId;

    @Builder
    public AddStudentCompensating(String eventId, String organizationId, String userId) {
        super(eventId);
        this.organizationId = organizationId;
        this.userId = userId;
    }
}
