package com.project.doit.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(){
        super("Not found exception.");
    };

    public NotFoundException(String object){
        super("Not found exception: " + object);
    }
}
