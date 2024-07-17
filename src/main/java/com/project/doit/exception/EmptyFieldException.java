package com.project.doit.exception;

public class EmptyFieldException extends RuntimeException {

    public EmptyFieldException(){super("Empty field exception");};

    public EmptyFieldException(String msg){super(msg);}
}
