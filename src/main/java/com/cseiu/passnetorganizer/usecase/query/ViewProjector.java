package com.cseiu.passnetorganizer.usecase.query;

import com.cseiu.passnetorganizer.domain.aggregate.vo.OrgId;
import com.cseiu.passnetorganizer.domain.aggregate.vo.UserId;
import com.cseiu.passnetorganizer.domain.exception.StudentNotFoundException;
import com.cseiu.passnetorganizer.domain.query.GetAllOrganizationQuery;
import com.cseiu.passnetorganizer.domain.query.GetDepartmentByOrg;
import com.cseiu.passnetorganizer.domain.query.GetStudentByUid;
import com.cseiu.passnetorganizer.domain.repository.DepartmentRepository;
import com.cseiu.passnetorganizer.domain.repository.OrganizationRepository;
import com.cseiu.passnetorganizer.domain.repository.StudentRepository;
import com.cseiu.passnetorganizer.domain.view.DepartmentView;
import com.cseiu.passnetorganizer.domain.view.OrganizationLiteView;
import com.cseiu.passnetorganizer.domain.view.MemberView;
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

    @Autowired
    public ViewProjector(OrganizationRepository organizationRepository, DepartmentRepository departmentRepository, StudentRepository studentRepository) {
        this.organizationRepository = organizationRepository;
        this.departmentRepository = departmentRepository;
        this.studentRepository = studentRepository;
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
}
