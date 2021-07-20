package com.cseiu.passnetorganizer.usecase.executor;

import com.cseiu.passnetorganizer.domain.command.AddNonStudentCommand;
import com.cseiu.passnetorganizer.domain.command.BaseCommand;
import com.cseiu.passnetorganizer.domain.compensating.AddNonStudentCompensating;
import com.cseiu.passnetorganizer.domain.compensating.BaseCompensating;
import com.cseiu.passnetorganizer.domain.exception.CommandNotCompatibleException;
import com.cseiu.passnetorganizer.usecase.feature.CompensatingConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "[AddNonStudentExecutor]")
public class AddNonStudentExecutor implements CommandExecutor, CompensatingHandler, CompensatingConverter<AddNonStudentCommand, AddNonStudentCompensating> {
    @Override
    public void execute(BaseCommand command) {
        log.info("Execute command");
    }

    @Override
    public void reverse(BaseCompensating compensating) {
        log.info("Execute compensating");
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
