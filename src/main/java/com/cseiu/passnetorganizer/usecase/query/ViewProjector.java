package com.cseiu.passnetorganizer.usecase.query;

import com.cseiu.passnetorganizer.domain.aggregate.vo.OrgId;
import com.cseiu.passnetorganizer.domain.aggregate.vo.SemesterId;
import com.cseiu.passnetorganizer.domain.aggregate.vo.UserId;
import com.cseiu.passnetorganizer.domain.exception.OrganizationNotFoundException;
import com.cseiu.passnetorganizer.domain.exception.SemesterNotFoundException;
import com.cseiu.passnetorganizer.domain.exception.StudentNotFoundException;
import com.cseiu.passnetorganizer.domain.query.*;
import com.cseiu.passnetorganizer.domain.repository.DepartmentRepository;
import com.cseiu.passnetorganizer.domain.repository.OrganizationRepository;
import com.cseiu.passnetorganizer.domain.repository.SemesterRepository;
import com.cseiu.passnetorganizer.domain.repository.StudentRepository;
import com.cseiu.passnetorganizer.domain.view.DepartmentView;
import com.cseiu.passnetorganizer.domain.view.MemberView;
import com.cseiu.passnetorganizer.domain.view.OrganizationLiteView;
import com.cseiu.passnetorganizer.domain.view.SemesterView;
import com.cseiu.passnetorganizer.usecase.mapper.DepartmentMapper;
import com.cseiu.passnetorganizer.usecase.mapper.OrganizationMapper;
import com.cseiu.passnetorganizer.usecase.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewProjector {
    private final OrganizationRepository organizationRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;

    @Autowired
    public ViewProjector(OrganizationRepository organizationRepository, DepartmentRepository departmentRepository, StudentRepository studentRepository, SemesterRepository semesterRepository) {
        this.organizationRepository = organizationRepository;
        this.departmentRepository = departmentRepository;
        this.studentRepository = studentRepository;
        this.semesterRepository = semesterRepository;
    }

    public List<OrganizationLiteView> query(GetAllOrganizationQuery query) {
        return organizationRepository.findAll().stream()
           .map(organization -> new OrganizationMapper(organization).toLiteView())
           .collect(Collectors.toList());
    }

    public List<DepartmentView> query(GetDepartmentByOrg query) {
        return departmentRepository.findByOrganizationId(new OrgId(query.getOrgId())).stream()
           .map(department -> new DepartmentMapper(department).toView())
           .collect(Collectors.toList());
    }

    public MemberView query(GetStudentByUid query) {
        return studentRepository.findStudentByUserId(new UserId(query.getUserId()))
           .map(student -> new StudentMapper(student).toStudentView())
           .orElseThrow(() -> new StudentNotFoundException(String.format("Student with uid [%s] not found", query.getUserId())));
    }

    public List<SemesterView> query(GetSemestersByOrg query) {
        return organizationRepository.findById(new OrgId(query.getOrganizationId()))
           .orElseThrow(() -> new OrganizationNotFoundException(String.format("Organization id [%s] not found", query.getOrganizationId())))
           .getSemesters().stream().map(
              semester -> SemesterView.builder()
                 .id(semester.getId().getValue())
                 .name(semester.getName().getValue())
                 .startMonth(semester.getStartMonth().name())
                 .endMonth(semester.getEndMonth().name()).build()
           )
           .collect(Collectors.toList()
           );
    }

    public SemesterView query(GetSemesterById query){
        var sem = semesterRepository.findById(new SemesterId(query.getSemId())).orElseThrow(() -> new SemesterNotFoundException(String.format("Semester with id %s not found", query.getSemId())));
           return SemesterView.builder()
              .id(sem.getId().getValue())
              .name(sem.getName().getValue())
              .startMonth(sem.getStartMonth().name())
              .endMonth(sem.getEndMonth().name()).build();
    }
}
