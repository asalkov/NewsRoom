package com.ansa.usermanagement;

public class User {

    private String username;
    private String password;
    private USER_ROLE userRole;

    public User(String username, String password, USER_ROLE userRole){
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public USER_ROLE getUserRole(){
        return userRole;
    }

    public void cleanPassword(){
        this.password = null;

    }
}
