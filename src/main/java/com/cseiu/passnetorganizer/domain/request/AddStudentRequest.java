package com.cseiu.passnetorganizer.domain.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class AddStudentRequest {
    @NotBlank
    private String cardId;
    @NotBlank
    private String profileId;
}
