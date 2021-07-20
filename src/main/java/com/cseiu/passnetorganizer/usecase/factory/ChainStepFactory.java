package com.cseiu.passnetorganizer.usecase.factory;

import com.cseiu.passnetorganizer.domain.enums.ExecutorChainStep;
import com.cseiu.passnetorganizer.usecase.chain.CompensatingBackupStep;
import com.cseiu.passnetorganizer.usecase.chain.UuidPreparationStep;
import com.cseiu.passnetorganizer.usecase.executor.CommandExecutor;
import com.cseiu.passnetorganizer.usecase.service.CompensatingBackupService;
import com.cseiu.passnetorganizer.usecase.service.CompensatingProvider;
import com.cseiu.passnetorganizer.usecase.service.UuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ChainStepFactory {
    private final UuidService uuidService;
    private final CompensatingProvider compensatingProvider;
    private final CompensatingBackupService compensatingBackupService;
    private final HttpServletRequest request;

    @Autowired
    public ChainStepFactory(UuidService uuidService, CompensatingProvider compensatingProvider, CompensatingBackupService compensatingBackupService, HttpServletRequest request) {
        this.uuidService = uuidService;
        this.compensatingProvider = compensatingProvider;
        this.compensatingBackupService = compensatingBackupService;
        this.request = request;
    }

    public CommandExecutor produce(ExecutorChainStep step, CommandExecutor commandExecutor) {
        switch (step) {
            case UUID_PREPARATION:
                return UuidPreparationStep.builder()
                   .uuidService(uuidService)
                   .executor(commandExecutor)
                   .build();
            case COMPENSATING_COMMAND_BACKUP:
                return CompensatingBackupStep.builder()
                   .executor(commandExecutor)
                   .request(request)
                   .compensatingBackupService(compensatingBackupService)
                   .compensatingProvider(compensatingProvider)
                   .build();
            default:
                return commandExecutor;
        }
    }
}
