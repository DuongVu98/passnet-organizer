package com.cseiu.passnetorganizer.usecase.executor;

import com.cseiu.passnetorganizer.domain.aggregate.entity.Department;
import com.cseiu.passnetorganizer.domain.aggregate.entity.Organization;
import com.cseiu.passnetorganizer.domain.aggregate.entity.Student;
import com.cseiu.passnetorganizer.domain.aggregate.vo.*;
import com.cseiu.passnetorganizer.domain.command.AddStudentCommand;
import com.cseiu.passnetorganizer.domain.command.BaseCommand;
import com.cseiu.passnetorganizer.domain.exception.CommandNotCompatibleException;
import com.cseiu.passnetorganizer.domain.exception.DepartmentNotFoundException;
import com.cseiu.passnetorganizer.domain.exception.OrganizationNotFoundException;
import com.cseiu.passnetorganizer.domain.exception.StudentCardIdExistedException;
import com.cseiu.passnetorganizer.domain.repository.DepartmentRepository;
import com.cseiu.passnetorganizer.domain.repository.OrganizationRepository;
import com.cseiu.passnetorganizer.domain.repository.StudentRepository;
import com.cseiu.passnetorganizer.usecase.feature.CommandConverter;
import com.cseiu.passnetorganizer.usecase.service.UuidService;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class AddStudentExecutor implements CommandExecutor, CommandConverter<AddStudentCommand> {

    private final OrganizationRepository organizationRepository;
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final UuidService uuidService;

    @Override
    public void execute(BaseCommand command) {
        var typedCommand = convert(command);

        this.organizationRepository.findById(new OrgId(typedCommand.getAggregateId())).ifPresentOrElse(
           org -> {
               var department = checkDepartmentExists(findDepartment(typedCommand.getDepartmentId()), org);
               if (studentRepository.existStudent(new StudentCardId(typedCommand.getCardId()), org)) {
                   throw new StudentCardIdExistedException("This card id has been already registered");
               } else {
                   var newStudent = Student.builder()
                      .id(new StudentId(uuidService.generate()))
                      .cardId(new StudentCardId(typedCommand.getCardId()))
                      .userId(new UserId(typedCommand.getUserId()))
                      .department(department)
                      .build();
                   department.addStudent(newStudent);
                   departmentRepository.save(department);
               }
           },
           () -> {
               throw new OrganizationNotFoundException(String.format("Organization [%s] not found", typedCommand.getAggregateId()));
           }
        );
    }

    @Override
    public AddStudentCommand convert(BaseCommand command) {
        if (command instanceof AddStudentCommand) {
            return (AddStudentCommand) command;
        } else {
            throw new CommandNotCompatibleException("This command must be AddStudentCommand");
        }
    }

    private Department findDepartment(String departmentId) {
        return this.departmentRepository.findById(new DepartmentId(departmentId))
           .orElseThrow(() -> new DepartmentNotFoundException(String.format("Department [%s] not found", departmentId)));
    }

    private Department checkDepartmentExists(Department department, Organization organization) {
        if (organization.containsDepartment(department)) {
            return department;
        } else {
            throw new DepartmentNotFoundException(String.format("Department [%s] not exists", department.getId().getValue()));
        }
    }
}
