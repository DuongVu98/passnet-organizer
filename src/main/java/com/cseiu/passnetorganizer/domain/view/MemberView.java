package com.cseiu.passnetorganizer.domain.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberView {
    @NonNull
    @JsonProperty("organization")
    private OrganizationLiteView organization;

    @NonNull
    @JsonProperty("profileType")
    private String profileType;

    @JsonProperty("department")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DepartmentLiteView department;

    @JsonProperty("cardId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cardId;
}
