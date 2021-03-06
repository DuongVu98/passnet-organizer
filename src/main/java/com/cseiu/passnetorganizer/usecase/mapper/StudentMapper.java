package com.cseiu.passnetorganizer.usecase.mapper;

import com.cseiu.passnetorganizer.domain.aggregate.entity.Student;
import com.cseiu.passnetorganizer.domain.view.MemberView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class StudentMapper {

    @NonNull
    private Student instance;

    public MemberView toStudentView() {
        if (Optional.ofNullable(instance.getCardId()).isPresent()){
            return MemberView.builder()
               .cardId(instance.getCardId().getValue())
               .department(new DepartmentMapper(instance.getDepartment()).toLiteView())
               .organization(new OrganizationMapper(instance.getDepartment().getOrganization()).toLiteView())
               .profileType("STUDENT")
               .build();
        } else {
            return MemberView.builder()
               .profileType("TEACHER")
               .organization(new OrganizationMapper(instance.getTeacherOrganization()).toLiteView())
               .build();
        }
    }
}
