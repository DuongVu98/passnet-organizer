package com.cseiu.passnetorganizer.domain.annotation;

import com.cseiu.passnetorganizer.domain.enums.ExecutorChainStep;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChainSteps {
    ExecutorChainStep[] steps();
}
