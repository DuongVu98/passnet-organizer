package com.cseiu.passnetorganizer.adapter.controller;

import com.cseiu.passnetorganizer.domain.command.AddDepartmentCommand;
import com.cseiu.passnetorganizer.domain.command.AddSemesterCommand;
import com.cseiu.passnetorganizer.domain.command.AddStudentCommand;
import com.cseiu.passnetorganizer.domain.command.CreateOrganizationCommand;
import com.cseiu.passnetorganizer.usecase.factory.ExecutorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandGateway {
    private final ExecutorFactory executorFactory;

    @Autowired
    public CommandGateway(ExecutorFactory executorFactory) {
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
}
