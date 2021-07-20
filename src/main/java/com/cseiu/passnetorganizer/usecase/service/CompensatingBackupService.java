package com.cseiu.passnetorganizer.usecase.service;

import com.cseiu.passnetorganizer.domain.compensating.BaseCompensating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j(topic = "[CompensatingBackupService]")
public class CompensatingBackupService {
    private final Map<String, BaseCompensating> compensatingCommandStore = new HashMap<>();

    public void addToStore(String eventId, BaseCompensating command) {
        log.info("store compensating command with eventId {}", eventId);

        this.compensatingCommandStore.put(eventId, command);
    }

    public BaseCompensating getFromStore(String eventId) {
        return this.compensatingCommandStore.get(eventId);
    }

    public void removeFromStore(String eventId) {
        this.compensatingCommandStore.remove(eventId);
    }
}
