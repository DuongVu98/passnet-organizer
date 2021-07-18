package com.cseiu.passnetorganizer.usecase.factory;

import com.cseiu.passnetorganizer.domain.enums.ExecutorChainStep;
import com.cseiu.passnetorganizer.usecase.chain.UuidPreparationStep;
import com.cseiu.passnetorganizer.usecase.executor.CommandExecutor;
import com.cseiu.passnetorganizer.usecase.service.UuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChainStepFactory {
    private final UuidService uuidService;

    @Autowired
    public ChainStepFactory(UuidService uuidService) {
        this.uuidService = uuidService;
    }

    public CommandExecutor produce(ExecutorChainStep step, CommandExecutor commandExecutor) {
        switch (step) {
            case UUID_PREPARATION:
                return UuidPreparationStep.builder()
                   .uuidService(uuidService)
                   .executor(commandExecutor)
                   .build();
            default:
                return commandExecutor;
        }
    }
}
