package com.cseiu.passnetorganizer.domain.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AddStudentCommand extends BaseCommand {
    String cardId;
    String profileId;
    String departmentId;
}
