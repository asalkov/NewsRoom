package com.ansa.configuration.xauth;


enum Status{
    OK,
    ERROR;
}
public class AuthResult {
    private Status status;
    private String token;
    private String message;

    public static AuthResult SUCCESS = new AuthResult(null, "", Status.OK);
    public static AuthResult DUPLICATE_USER = new AuthResult("", "Duplicate user", Status.ERROR);

    public AuthResult(String token, String message, Status status){
        this.status = status;
        this.token = token;
        this.message = message;
    }

    public Status getStatus(){
        return status;
    }

    public String getToken(){
        return token;
    }

    public String getMessage() {return message;}
}
