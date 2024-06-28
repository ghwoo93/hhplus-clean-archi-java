package io.hhplus.clean.architect.lecture.helper;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

@Component
public class FairLockHelper {
    private final Lock lock = new ReentrantLock(true); // Fair lock

    public boolean executeWithLock(BooleanSupplier task) throws Exception {
        lock.lock();
        try {
            return task.getAsBoolean();
        } finally {
            lock.unlock();
        }
    }

    @FunctionalInterface
    public interface BooleanSupplier {
        boolean getAsBoolean() throws Exception;
    }
}