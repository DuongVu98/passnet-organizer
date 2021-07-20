package com.cseiu.passnetorganizer.domain.command;

import com.cseiu.passnetorganizer.domain.aggregate.vo.AcademicMonth;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AddSemesterCommand extends BaseCommand {
    String name;
    AcademicMonth startMonth;
    AcademicMonth endMonth;
}
