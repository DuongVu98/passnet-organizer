package com.cseiu.passnetorganizer.usecase.chain;

import com.cseiu.passnetorganizer.domain.command.BaseCommand;
import com.cseiu.passnetorganizer.usecase.executor.CommandExecutor;
import com.cseiu.passnetorganizer.usecase.service.CompensatingBackupService;
import com.cseiu.passnetorganizer.usecase.service.CompensatingProvider;
import lombok.Builder;

import javax.servlet.http.HttpServletRequest;

public class CompensatingBackupStep extends ExecutorChain {

    private final CompensatingBackupService compensatingBackupService;
    private final CompensatingProvider compensatingProvider;
    private final HttpServletRequest request;

    @Builder
    public CompensatingBackupStep(CommandExecutor executor, CompensatingBackupService compensatingBackupService, CompensatingProvider compensatingProvider, HttpServletRequest request) {
        super(executor);
        this.compensatingBackupService = compensatingBackupService;
        this.compensatingProvider = compensatingProvider;
        this.request = request;
    }

    @Override
    public void execute(BaseCommand command) {
        backup(command);
        this.executor.execute(command);
    }

    private void backup(BaseCommand command) {
        var compensating = compensatingProvider.build(command);
        compensatingBackupService.addToStore(compensating.getEventId(), compensating);
        this.request.getSession().setAttribute("eventId", compensating.getEventId());
    }
}
