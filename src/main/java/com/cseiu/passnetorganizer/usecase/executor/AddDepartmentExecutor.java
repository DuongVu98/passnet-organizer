package com.cseiu.passnetorganizer.usecase.executor;

import com.cseiu.passnetorganizer.domain.aggregate.entity.Department;
import com.cseiu.passnetorganizer.domain.aggregate.entity.Organization;
import com.cseiu.passnetorganizer.domain.aggregate.vo.DepartmentCode;
import com.cseiu.passnetorganizer.domain.aggregate.vo.DepartmentId;
import com.cseiu.passnetorganizer.domain.aggregate.vo.Name;
import com.cseiu.passnetorganizer.domain.aggregate.vo.OrgId;
import com.cseiu.passnetorganizer.domain.command.AddDepartmentCommand;
import com.cseiu.passnetorganizer.domain.command.BaseCommand;
import com.cseiu.passnetorganizer.domain.exception.CommandNotCompatibleException;
import com.cseiu.passnetorganizer.domain.exception.DepartmentAlreadyExistException;
import com.cseiu.passnetorganizer.domain.exception.OrganizationNotFoundException;
import com.cseiu.passnetorganizer.domain.repository.DepartmentRepository;
import com.cseiu.passnetorganizer.domain.repository.OrganizationRepository;
import com.cseiu.passnetorganizer.usecase.feature.CommandConverter;
import com.cseiu.passnetorganizer.usecase.service.UuidService;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class AddDepartmentExecutor implements CommandExecutor, CommandConverter<AddDepartmentCommand> {

    private final OrganizationRepository organizationRepository;
    private final DepartmentRepository departmentRepository;
    private final UuidService uuidService;

    @Override
    public void execute(BaseCommand command) {
        var typedCommand = convert(command);
        this.organizationRepository.findById(new OrgId(typedCommand.getAggregateId())).ifPresentOrElse(
           org -> {
               checkDepartmentCodeExist(typedCommand.getCode(), org);
               var newDepartment = Department.builder()
                  .id(new DepartmentId(uuidService.generate()))
                  .name(new Name(typedCommand.getName()))
                  .code(new DepartmentCode(typedCommand.getCode()))
                  .organization(org)
                  .build();
               org.addDepartment(newDepartment);
               this.organizationRepository.save(org);
           },
           () -> {
               throw new OrganizationNotFoundException(String.format("Organization [%s] not found", typedCommand.getAggregateId()));
           }
        );
    }

    @Override
    public AddDepartmentCommand convert(BaseCommand command) {
        if (command instanceof AddDepartmentCommand) {
            return (AddDepartmentCommand) command;
        } else {
            throw new CommandNotCompatibleException("This command must be AddDepartmentCommand");
        }
    }

    private void checkDepartmentCodeExist(String departmentCode, Organization organization) {
        if(departmentRepository.existsDepartmentByCode(new DepartmentCode(departmentCode), organization)){
            throw new DepartmentAlreadyExistException(String.format("Department with code [%s] already existed.", departmentCode));
        }
    }
}
