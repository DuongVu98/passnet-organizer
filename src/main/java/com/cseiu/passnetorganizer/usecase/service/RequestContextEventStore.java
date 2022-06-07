package com.cseiu.passnetorganizer.usecase.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class RequestContextEventStore {
    public static final String EMPTY = "";
    private boolean isLocked;
    private final Lock lock;

    @Getter
    private String eventId;

    public RequestContextEventStore() {
        this.eventId = "";
        this.lock = new ReentrantLock();
        this.isLocked = false;
    }

    public void setValue(String value) {
        this.lock.lock();
        this.isLocked = true;
        this.eventId = value;
    }

    public void release() {
        this.eventId = EMPTY;
        if (this.isLocked) {
            lock.unlock();
            this.isLocked = false;
        }
    }
}
