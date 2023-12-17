package com.rafirs.projectutsppk.exception;

/**
 *
 * @author RafiRS
 */

public class PendaftaranNotFoundException extends RuntimeException {

    public PendaftaranNotFoundException(String message) {
        super(message);
    }

    public PendaftaranNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

