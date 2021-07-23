package com.cseiu.passnetorganizer.domain.command;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AddNonStudentCommand extends BaseCommand{
    String userId;

    @Builder
    public AddNonStudentCommand(String aggregateId, String userId) {
        super(aggregateId);
        this.userId = userId;
    }
}
