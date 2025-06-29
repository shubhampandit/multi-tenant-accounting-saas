package com.shubham.saas.error;

public class ClientSideException extends RuntimeException{
    private String message;

    public ClientSideException(){
        super();
    }

    public ClientSideException(String message){
        super(message);
    }
}
