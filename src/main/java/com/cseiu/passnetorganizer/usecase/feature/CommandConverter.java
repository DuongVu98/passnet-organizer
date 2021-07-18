package com.cseiu.passnetorganizer.usecase.feature;

import com.cseiu.passnetorganizer.domain.command.BaseCommand;

public interface CommandConverter<T> {
    T convert(BaseCommand command);
}
