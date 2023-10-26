package com.example.processedfuturemovement.exceptions;

import java.io.IOException;

public class FileParseException extends RuntimeException{

    public FileParseException(String message, IOException cause) {
        super(message, cause);
    }
}
