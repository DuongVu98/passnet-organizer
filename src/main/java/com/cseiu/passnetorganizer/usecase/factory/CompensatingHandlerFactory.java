package com.cseiu.passnetorganizer.usecase.factory;

import com.cseiu.passnetorganizer.domain.compensating.AddNonStudentCompensating;
import com.cseiu.passnetorganizer.domain.compensating.AddStudentCompensating;
import com.cseiu.passnetorganizer.usecase.executor.CompensatingHandler;
import com.cseiu.passnetorganizer.usecase.service.ExecutorProvider;
import org.springframework.stereotype.Service;

@Service
public class CompensatingHandlerFactory {

    private final ExecutorProvider executorProvider;

    public CompensatingHandlerFactory(ExecutorProvider executorProvider) {
        this.executorProvider = executorProvider;
    }

    public CompensatingHandler produce(AddStudentCompensating compensating) {
        return executorProvider.produceAddStudentExecutor();
    }

    public CompensatingHandler produce(AddNonStudentCompensating compensating){
        return executorProvider.produceAddNonStudentExecutor();
    }
}
