package com.cseiu.passnetorganizer.usecase.executor;

import com.cseiu.passnetorganizer.domain.command.BaseCommand;

import javax.transaction.Transactional;

public interface CommandExecutor {
    @Transactional
    void execute(BaseCommand command);
}
