package com.cseiu.passnetorganizer.usecase.service;

import com.cseiu.passnetorganizer.domain.command.AddStudentCommand;
import com.cseiu.passnetorganizer.domain.command.BaseCommand;
import com.cseiu.passnetorganizer.domain.compensating.AddStudentCompensating;
import com.cseiu.passnetorganizer.domain.compensating.BaseCompensating;
import com.cseiu.passnetorganizer.domain.exception.WrongCommandTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensatingProvider {
    private final UuidService uuidService;
    private final RequestContextEventStore requestContextEventStore;

    @Autowired
    public CompensatingProvider(UuidService uuidService, RequestContextEventStore requestContextEventStore) {
        this.uuidService = uuidService;
        this.requestContextEventStore = requestContextEventStore;
    }

    public BaseCompensating build(BaseCommand command) {
        if (command instanceof AddStudentCommand) {
            return build((AddStudentCommand) command);
        } else {
            throw new WrongCommandTypeException("transaction command is not included.");
        }
    }

    public AddStudentCompensating build(AddStudentCommand command) {
        return AddStudentCompensating.builder()
           .eventId(uuidService.generate())
           .organizationId(requestContextEventStore.getEventId())
           .userId(command.getUserId())
           .build();
    }
}
