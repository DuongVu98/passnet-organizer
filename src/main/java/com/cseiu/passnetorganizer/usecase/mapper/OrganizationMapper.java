package com.cseiu.passnetorganizer.usecase.mapper;

import com.cseiu.passnetorganizer.domain.aggregate.entity.Organization;
import com.cseiu.passnetorganizer.domain.view.OrganizationLiteView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class OrganizationMapper {

    @NonNull
    private Organization instance;

    public OrganizationLiteView toLiteView(){
        return OrganizationLiteView.builder()
           .id(instance.getId().getValue())
           .name(instance.getName().getValue())
           .location(instance.getLocation().getValue())
           .build();
    }
}
