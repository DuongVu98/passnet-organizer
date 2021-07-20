package com.cseiu.passnetorganizer.usecase.chain;

import com.cseiu.passnetorganizer.domain.command.BaseCommand;
import com.cseiu.passnetorganizer.usecase.executor.CommandExecutor;
import com.cseiu.passnetorganizer.usecase.service.CompensatingBackupService;
import com.cseiu.passnetorganizer.usecase.service.CompensatingProvider;
import com.cseiu.passnetorganizer.usecase.service.RequestContextEventStore;
import lombok.Builder;

public class CompensatingBackupStep extends ExecutorChain {

    private final CompensatingBackupService compensatingBackupService;
    private final CompensatingProvider compensatingProvider;
    private final RequestContextEventStore requestContextEventStore;

    @Builder
    public CompensatingBackupStep(CommandExecutor executor, CompensatingBackupService compensatingBackupService, CompensatingProvider compensatingProvider, RequestContextEventStore requestContextEventStore) {
        super(executor);
        this.compensatingBackupService = compensatingBackupService;
        this.compensatingProvider = compensatingProvider;
        this.requestContextEventStore = requestContextEventStore;
    }


    @Override
    public void execute(BaseCommand command) {
        backup(command);
        this.executor.execute(command);
    }

    private void backup(BaseCommand command) {
        var compensating = compensatingProvider.build(command);
        compensatingBackupService.addToStore(compensating.getEventId(), compensating);
        requestContextEventStore.release();
    }
}
