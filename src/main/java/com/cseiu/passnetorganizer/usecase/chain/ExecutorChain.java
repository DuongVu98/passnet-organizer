package com.cseiu.passnetorganizer.usecase.chain;

import com.cseiu.passnetorganizer.usecase.executor.CommandExecutor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class ExecutorChain implements CommandExecutor {
    protected CommandExecutor executor;
}
