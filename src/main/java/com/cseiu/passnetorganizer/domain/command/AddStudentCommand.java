package com.cseiu.passnetorganizer.domain.command;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AddStudentCommand extends BaseCommand {
    String cardId;
    String userId;
    String departmentId;

    @Builder
    public AddStudentCommand(String aggregateId, String cardId, String userId, String departmentId) {
        super(aggregateId);
        this.cardId = cardId;
        this.userId = userId;
        this.departmentId = departmentId;
    }
}
