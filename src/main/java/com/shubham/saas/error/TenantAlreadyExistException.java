package com.shubham.saas.error;

public class TenantAlreadyExistException extends ClientSideException{
    private String message;

    public TenantAlreadyExistException(){
        super();
    }

    public TenantAlreadyExistException(String message){
        super(message);
    }
}
