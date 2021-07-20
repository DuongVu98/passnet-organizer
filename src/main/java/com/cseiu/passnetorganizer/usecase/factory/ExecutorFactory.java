package com.cseiu.passnetorganizer.usecase.factory;

import com.cseiu.passnetorganizer.domain.annotation.ChainSteps;
import com.cseiu.passnetorganizer.domain.command.AddDepartmentCommand;
import com.cseiu.passnetorganizer.domain.command.AddSemesterCommand;
import com.cseiu.passnetorganizer.domain.command.AddStudentCommand;
import com.cseiu.passnetorganizer.domain.command.CreateOrganizationCommand;
import com.cseiu.passnetorganizer.domain.enums.ExecutorChainStep;
import com.cseiu.passnetorganizer.domain.repository.DepartmentRepository;
import com.cseiu.passnetorganizer.domain.repository.OrganizationRepository;
import com.cseiu.passnetorganizer.domain.repository.StudentRepository;
import com.cseiu.passnetorganizer.usecase.executor.*;
import com.cseiu.passnetorganizer.usecase.service.UuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutorFactory {
    private final OrganizationRepository organizationRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;
    private final UuidService uuidService;

    @Autowired
    public ExecutorFactory(OrganizationRepository organizationRepository, DepartmentRepository departmentRepository, StudentRepository studentRepository, UuidService uuidService) {
        this.organizationRepository = organizationRepository;
        this.departmentRepository = departmentRepository;
        this.studentRepository = studentRepository;
        this.uuidService = uuidService;
    }

    @ChainSteps(steps = {ExecutorChainStep.UUID_PREPARATION})
    public CommandExecutor produce(CreateOrganizationCommand command) {
        return CreateOrganizationExecutor.builder()
           .organizationRepository(organizationRepository)
           .build();
    }

    public CommandExecutor produce(AddDepartmentCommand command) {
        return AddDepartmentExecutor.builder()
           .organizationRepository(organizationRepository)
           .departmentRepository(departmentRepository)
           .uuidService(uuidService)
           .build();
    }

    public CommandExecutor produce(AddSemesterCommand command) {
        return AddSemesterExecutor.builder()
           .organizationRepository(organizationRepository)
           .uuidService(uuidService)
           .build();
    }

    public CommandExecutor produce(AddStudentCommand command) {
        return AddStudentExecutor.builder()
           .organizationRepository(organizationRepository)
           .departmentRepository(departmentRepository)
           .studentRepository(studentRepository)
           .uuidService(uuidService)
           .build();
    }
}
