package com.cseiu.passnetorganizer.domain.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentView {
    @NonNull
    @JsonProperty("id")
    private String id;

    @NonNull
    @JsonProperty("name")
    private String name;

    @NonNull
    @JsonProperty("code")
    private String code;

    @JsonProperty("organization")
    private OrganizationLiteView organization;

    @JsonProperty("numberOfStudent")
    private Integer numberOfStudent;
}
