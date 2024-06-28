package io.hhplus.clean.architect.lecture.exception;

public class LectureBusinessException extends RuntimeException {
    public LectureBusinessException(String message) {
        super(message);
    }

    public LectureBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
