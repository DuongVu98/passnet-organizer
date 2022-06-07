package com.cseiu.passnetorganizer.usecase.executor;

import com.cseiu.passnetorganizer.domain.aggregate.entity.Student;
import com.cseiu.passnetorganizer.domain.aggregate.vo.OrgId;
import com.cseiu.passnetorganizer.domain.aggregate.vo.StudentId;
import com.cseiu.passnetorganizer.domain.aggregate.vo.UserId;
import com.cseiu.passnetorganizer.domain.command.AddNonStudentCommand;
import com.cseiu.passnetorganizer.domain.command.BaseCommand;
import com.cseiu.passnetorganizer.domain.compensating.AddNonStudentCompensating;
import com.cseiu.passnetorganizer.domain.compensating.BaseCompensating;
import com.cseiu.passnetorganizer.domain.exception.CommandNotCompatibleException;
import com.cseiu.passnetorganizer.domain.exception.OrganizationNotFoundException;
import com.cseiu.passnetorganizer.domain.exception.StudentNotFoundException;
import com.cseiu.passnetorganizer.domain.repository.OrganizationRepository;
import com.cseiu.passnetorganizer.domain.repository.StudentRepository;
import com.cseiu.passnetorganizer.usecase.feature.CompensatingConverter;
import com.cseiu.passnetorganizer.usecase.service.UuidService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@AllArgsConstructor
@Slf4j(topic = "[AddNonStudentExecutor]")
public class AddNonStudentExecutor implements CommandExecutor, CompensatingHandler, CompensatingConverter<AddNonStudentCommand, AddNonStudentCompensating> {

    private final OrganizationRepository organizationRepository;
    private final StudentRepository studentRepository;
    private final UuidService uuidService;

    @Override
    public void execute(BaseCommand command) {
        var typedCommand = convertCommand(command);
        this.organizationRepository.findById(new OrgId(typedCommand.getAggregateId())).ifPresentOrElse(
           organization -> {
               var newNonStudent = Student.builder()
                  .id(new StudentId(uuidService.generate()))
                  .userId(new UserId(typedCommand.getUserId()))
                  .teacherOrganization(organization)
                  .build();
               this.studentRepository.save(newNonStudent);
           },
           () -> {
               throw new OrganizationNotFoundException(String.format("Organization [%s] not found", typedCommand.getAggregateId()));
           }
        );
    }

    @Override
    public void reverse(BaseCompensating compensating) {
        var typedCommand = convertCompensating(compensating);
        studentRepository.findStudentByUserId(new UserId(typedCommand.getUserId()))
           .ifPresentOrElse(
              this.studentRepository::delete,
              () -> {
                  throw new StudentNotFoundException(String.format("Student with uid %s not found", typedCommand.getUserId()));
              }
           );
    }

    @Override
    public AddNonStudentCommand convertCommand(BaseCommand command) {
        if(command instanceof AddNonStudentCommand) {
            return (AddNonStudentCommand) command;
        } else {
            throw new CommandNotCompatibleException("This command must be AddNonStudentCommand");
        }
    }

    @Override
    public AddNonStudentCompensating convertCompensating(BaseCompensating command) {
        if(command instanceof AddNonStudentCompensating) {
            return (AddNonStudentCompensating) command;
        } else {
            throw new CommandNotCompatibleException("This command must be AddNonStudentCommand");
        }
    }
}
