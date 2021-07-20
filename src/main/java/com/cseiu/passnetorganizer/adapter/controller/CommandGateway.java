package com.cseiu.passnetorganizer.adapter.controller;

import com.cseiu.passnetorganizer.domain.command.*;
import com.cseiu.passnetorganizer.usecase.factory.CommandExecutorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandGateway {
    private final CommandExecutorFactory executorFactory;

    @Autowired
    public CommandGateway(CommandExecutorFactory executorFactory) {
        this.executorFactory = executorFactory;
    }

    public void send(CreateOrganizationCommand command) {
        var executor = executorFactory.produce(command);
        executor.execute(command);
    }

    public void send(AddDepartmentCommand command) {
        var executor = executorFactory.produce(command);
        executor.execute(command);
    }

    public void send(AddSemesterCommand command) {
        var executor = executorFactory.produce(command);
        executor.execute(command);
    }

    public void send(AddStudentCommand command) {
        var executor = executorFactory.produce(command);
        executor.execute(command);
    }

    public void send(AddNonStudentCommand command) {
        var executor = executorFactory.produce(command);
        executor.execute(command);
    }
}
