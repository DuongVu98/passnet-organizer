package com.cseiu.passnetorganizer.usecase.chain;

import com.cseiu.passnetorganizer.domain.command.BaseCommand;
import com.cseiu.passnetorganizer.usecase.executor.CommandExecutor;

public class EventPublishStep extends ExecutorChain{
    public EventPublishStep(CommandExecutor executor) {
        super(executor);
    }

    @Override
    public void execute(BaseCommand command) {

    }
}
