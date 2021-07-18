package com.cseiu.passnetorganizer.usecase.chain;

import com.cseiu.passnetorganizer.domain.command.BaseCommand;
import com.cseiu.passnetorganizer.usecase.executor.CommandExecutor;
import com.cseiu.passnetorganizer.usecase.service.UuidService;
import lombok.Builder;

public class UuidPreparationStep extends ExecutorChain {

    private final UuidService uuidService;

    @Builder
    public UuidPreparationStep(CommandExecutor executor, UuidService uuidService) {
        super(executor);
        this.uuidService = uuidService;
    }

    @Override
    public void execute(BaseCommand command) {
        command.setAggregateId(uuidService.generate());
        this.executor.execute(command);
    }
}
