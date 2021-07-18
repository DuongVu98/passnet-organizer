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
public class CreateOrganizationRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String location;
}
