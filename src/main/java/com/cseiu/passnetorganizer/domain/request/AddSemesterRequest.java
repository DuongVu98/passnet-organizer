package com.cseiu.passnetorganizer.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class AddSemesterRequest {
    @NotBlank
    private String name;

    @Min(1)
    @Max(12)
    @NotNull
    private Integer startMonth;

    @Min(1)
    @Max(12)
    @NotNull
    private Integer endMonth;
}
