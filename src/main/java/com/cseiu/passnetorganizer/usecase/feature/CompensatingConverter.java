package com.cseiu.passnetorganizer.usecase.feature;

import com.cseiu.passnetorganizer.domain.compensating.BaseCompensating;

public interface CompensatingConverter<T, S> extends CommandConverter<T> {
    S convertCompensating(BaseCompensating command);
}
