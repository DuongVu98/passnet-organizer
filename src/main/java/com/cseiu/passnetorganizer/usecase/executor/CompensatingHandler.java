package com.cseiu.passnetorganizer.usecase.executor;

import com.cseiu.passnetorganizer.domain.compensating.BaseCompensating;

public interface CompensatingHandler {
    void reverse(BaseCompensating compensating);
}
