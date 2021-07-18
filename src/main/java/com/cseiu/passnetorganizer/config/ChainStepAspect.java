package com.cseiu.passnetorganizer.config;

import com.cseiu.passnetorganizer.domain.annotation.ChainSteps;
import com.cseiu.passnetorganizer.domain.enums.ExecutorChainStep;
import com.cseiu.passnetorganizer.usecase.executor.CommandExecutor;
import com.cseiu.passnetorganizer.usecase.factory.ChainStepFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ChainStepAspect {
    private final ChainStepFactory chainStepFactory;

    @Autowired
    public ChainStepAspect(ChainStepFactory chainStepFactory) {
        this.chainStepFactory = chainStepFactory;
    }

    @Pointcut("@annotation(com.cseiu.passnetorganizer.domain.annotation.ChainSteps)")
    public void chainStepPointcut() {
    }

    @Around("chainStepPointcut()")
    public Object chainStepAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        var method = signature.getMethod();

        var stepAnnotation = AnnotationUtils.findAnnotation(method, ChainSteps.class);
        var executor = (CommandExecutor) joinPoint.proceed();

        for (ExecutorChainStep step : stepAnnotation.steps()) {
            executor = chainStepFactory.produce(step, executor);
        }
        return executor;
    }
}
