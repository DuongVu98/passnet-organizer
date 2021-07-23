package com.cseiu.passnetorganizer.usecase.service;

import com.cseiu.passnetorganizer.domain.repository.DepartmentRepository;
import com.cseiu.passnetorganizer.domain.repository.OrganizationRepository;
import com.cseiu.passnetorganizer.domain.repository.StudentRepository;
import com.cseiu.passnetorganizer.usecase.executor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutorProvider {
    private final OrganizationRepository organizationRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;
    private final UuidService uuidService;

    @Autowired
    public ExecutorProvider(OrganizationRepository organizationRepository, DepartmentRepository departmentRepository, StudentRepository studentRepository, UuidService uuidService) {
        this.organizationRepository = organizationRepository;
        this.departmentRepository = departmentRepository;
        this.studentRepository = studentRepository;
        this.uuidService = uuidService;
    }

    public CreateOrganizationExecutor produceCreateOrganizationExecutor() {
        return CreateOrganizationExecutor.builder()
           .organizationRepository(organizationRepository)
           .build();
    }

    public AddDepartmentExecutor produceAddDepartmentExecutor() {
        return AddDepartmentExecutor.builder()
           .organizationRepository(organizationRepository)
           .departmentRepository(departmentRepository)
           .uuidService(uuidService)
           .build();
    }

    public AddSemesterExecutor produceAddSemesterExecutor() {
        return AddSemesterExecutor.builder()
           .organizationRepository(organizationRepository)
           .uuidService(uuidService)
           .build();
    }

    public AddStudentExecutor produceAddStudentExecutor() {
        return AddStudentExecutor.builder()
           .organizationRepository(organizationRepository)
           .departmentRepository(departmentRepository)
           .studentRepository(studentRepository)
           .uuidService(uuidService)
           .build();
    }

    public AddNonStudentExecutor produceAddNonStudentExecutor(){
        return AddNonStudentExecutor.builder()
           .studentRepository(studentRepository)
           .organizationRepository(organizationRepository)
           .uuidService(uuidService)
           .build();
    }
}
