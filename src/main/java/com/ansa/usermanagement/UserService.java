package com.ansa.usermanagement;

public interface UserService {
    public User registerUser(String username, String password);
    public User getUserByToken(String token);

    public User getUserByUserName(String userName);

    public User findUser(String userName);
}
