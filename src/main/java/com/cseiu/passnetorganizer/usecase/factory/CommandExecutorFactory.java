package com.cseiu.passnetorganizer.usecase.factory;

import com.cseiu.passnetorganizer.domain.annotation.ChainSteps;
import com.cseiu.passnetorganizer.domain.command.*;
import com.cseiu.passnetorganizer.domain.enums.ExecutorChainStep;
import com.cseiu.passnetorganizer.usecase.executor.CommandExecutor;
import com.cseiu.passnetorganizer.usecase.service.ExecutorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandExecutorFactory {
    private final ExecutorProvider executorProvider;

    @Autowired
    public CommandExecutorFactory(ExecutorProvider executorProvider) {
        this.executorProvider = executorProvider;
    }

    @ChainSteps(steps = {ExecutorChainStep.UUID_PREPARATION})
    public CommandExecutor produce(CreateOrganizationCommand command) {
        return executorProvider.produceCreateOrganizationExecutor();
    }

    public CommandExecutor produce(AddDepartmentCommand command) {
        return executorProvider.produceAddDepartmentExecutor();
    }

    public CommandExecutor produce(AddSemesterCommand command) {
        return executorProvider.produceAddSemesterExecutor();
    }

    @ChainSteps(steps = {ExecutorChainStep.COMPENSATING_COMMAND_BACKUP})
    public CommandExecutor produce(AddStudentCommand command) {
        return executorProvider.produceAddStudentExecutor();
    }

    @ChainSteps(steps = {ExecutorChainStep.COMPENSATING_COMMAND_BACKUP})
    public CommandExecutor produce(AddNonStudentCommand command){
        return executorProvider.produceAddNonStudentExecutor();
    }
}
