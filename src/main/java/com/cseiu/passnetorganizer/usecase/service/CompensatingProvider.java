package com.cseiu.passnetorganizer.usecase.service;

import com.cseiu.passnetorganizer.domain.command.AddNonStudentCommand;
import com.cseiu.passnetorganizer.domain.command.AddStudentCommand;
import com.cseiu.passnetorganizer.domain.command.BaseCommand;
import com.cseiu.passnetorganizer.domain.compensating.AddNonStudentCompensating;
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
        } else if(command instanceof AddNonStudentCommand){
            return build((AddNonStudentCommand) command);
        }
        else {
            throw new WrongCommandTypeException("transaction command is not included.");
        }
    }

    public AddStudentCompensating build(AddStudentCommand command) {
        return AddStudentCompensating.builder()
           .eventId(requestContextEventStore.getEventId())
           .organizationId(command.getAggregateId())
           .userId(command.getUserId())
           .build();
    }

    public AddNonStudentCompensating build(AddNonStudentCommand command) {
        return AddNonStudentCompensating.builder()
           .eventId(requestContextEventStore.getEventId())
           .organizationId(command.getAggregateId())
           .userId(command.getUserId())
           .build();
    }
}
