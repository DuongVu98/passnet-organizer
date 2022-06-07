package com.cseiu.passnetorganizer.usecase.factory;

import com.cseiu.passnetorganizer.domain.enums.ExecutorChainStep;
import com.cseiu.passnetorganizer.usecase.chain.CompensatingBackupStep;
import com.cseiu.passnetorganizer.usecase.chain.UuidPreparationStep;
import com.cseiu.passnetorganizer.usecase.executor.CommandExecutor;
import com.cseiu.passnetorganizer.usecase.service.CompensatingBackupService;
import com.cseiu.passnetorganizer.usecase.service.CompensatingProvider;
import com.cseiu.passnetorganizer.usecase.service.RequestContextEventStore;
import com.cseiu.passnetorganizer.usecase.service.UuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChainStepFactory {
    private final UuidService uuidService;
    private final CompensatingProvider compensatingProvider;
    private final CompensatingBackupService compensatingBackupService;
    private final RequestContextEventStore requestContextEventStore;

    @Autowired
    public ChainStepFactory(UuidService uuidService, CompensatingProvider compensatingProvider, CompensatingBackupService compensatingBackupService, RequestContextEventStore requestContextEventStore) {
        this.uuidService = uuidService;
        this.compensatingProvider = compensatingProvider;
        this.compensatingBackupService = compensatingBackupService;
        this.requestContextEventStore = requestContextEventStore;
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
                   .requestContextEventStore(requestContextEventStore)
                   .compensatingBackupService(compensatingBackupService)
                   .compensatingProvider(compensatingProvider)
                   .build();
            default:
                return commandExecutor;
        }
    }
}
