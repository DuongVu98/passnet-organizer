package com.cseiu.passnetorganizer.usecase.mapper;

import com.cseiu.passnetorganizer.domain.aggregate.entity.Department;
import com.cseiu.passnetorganizer.domain.view.DepartmentView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class DepartmentMapper {

    @NonNull
    private Department instance;

    public DepartmentView toLiteView() {
        return DepartmentView.builder()
           .id(instance.getId().getValue())
           .name(instance.getName().getValue())
           .code(instance.getCode().getValue())
           .build();
    }
}
