package com.cseiu.passnetorganizer.usecase.executor;

import com.cseiu.passnetorganizer.domain.aggregate.entity.Organization;
import com.cseiu.passnetorganizer.domain.aggregate.vo.Location;
import com.cseiu.passnetorganizer.domain.aggregate.vo.Name;
import com.cseiu.passnetorganizer.domain.aggregate.vo.OrgId;
import com.cseiu.passnetorganizer.domain.command.BaseCommand;
import com.cseiu.passnetorganizer.domain.command.CreateOrganizationCommand;
import com.cseiu.passnetorganizer.domain.exception.CommandNotCompatibleException;
import com.cseiu.passnetorganizer.domain.repository.OrganizationRepository;
import com.cseiu.passnetorganizer.usecase.feature.CommandConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class CreateOrganizationExecutor implements CommandExecutor, CommandConverter<CreateOrganizationCommand> {

    private final OrganizationRepository organizationRepository;

    @Override
    public void execute(BaseCommand command) {
        var typedCommand = convertCommand(command);
        var newOrg = Organization.builder()
           .id(new OrgId(typedCommand.getAggregateId()))
           .name(new Name(typedCommand.getName()))
           .location(new Location(typedCommand.getLocation()))
           .build();

        this.organizationRepository.save(newOrg);
    }

    @Override
    public CreateOrganizationCommand convertCommand(BaseCommand command) {
        if (command instanceof CreateOrganizationCommand) {
            return (CreateOrganizationCommand) command;
        } else {
            throw new CommandNotCompatibleException("This command must be CreateOrganizationCommand");
        }
    }
}
