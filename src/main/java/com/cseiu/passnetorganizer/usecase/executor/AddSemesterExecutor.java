package com.cseiu.passnetorganizer.usecase.executor;

import com.cseiu.passnetorganizer.domain.aggregate.entity.Semester;
import com.cseiu.passnetorganizer.domain.aggregate.vo.Name;
import com.cseiu.passnetorganizer.domain.aggregate.vo.OrgId;
import com.cseiu.passnetorganizer.domain.aggregate.vo.SemesterId;
import com.cseiu.passnetorganizer.domain.command.AddSemesterCommand;
import com.cseiu.passnetorganizer.domain.command.BaseCommand;
import com.cseiu.passnetorganizer.domain.exception.CommandNotCompatibleException;
import com.cseiu.passnetorganizer.domain.exception.OrganizationNotFoundException;
import com.cseiu.passnetorganizer.domain.repository.OrganizationRepository;
import com.cseiu.passnetorganizer.usecase.feature.CommandConverter;
import com.cseiu.passnetorganizer.usecase.service.UuidService;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class AddSemesterExecutor implements CommandExecutor, CommandConverter<AddSemesterCommand> {

    private final OrganizationRepository organizationRepository;
    private final UuidService uuidService;

    @Override
    public void execute(BaseCommand command) {
        var typedCommand = convert(command);
        this.organizationRepository.findById(new OrgId(typedCommand.getAggregateId())).ifPresentOrElse(
           org -> {
               var newSem = Semester.builder()
                  .id(new SemesterId(uuidService.generate()))
                  .name(new Name(typedCommand.getName()))
                  .startMonth(typedCommand.getStartMonth())
                  .endMonth(typedCommand.getEndMonth())
                  .organization(org)
                  .build();
               org.addSemester(newSem);
               this.organizationRepository.save(org);
           },
           () -> {
               throw new OrganizationNotFoundException(String.format("Organization [%s] not found", typedCommand.getAggregateId()));
           }
        );
    }

    @Override
    public AddSemesterCommand convert(BaseCommand command) {
        if (command instanceof AddSemesterCommand) {
            return (AddSemesterCommand) command;
        } else {
            throw new CommandNotCompatibleException("This command must be AddSemesterCommand");
        }
    }
}
