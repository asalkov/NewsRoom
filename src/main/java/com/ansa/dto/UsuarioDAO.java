package com.ansa.dto;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class UsuarioDAO {
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    List<Usuario> details = Arrays.<Usuario>asList(
            new Usuario("user", "user", ROLE_USER),
            new Usuario("admin", "admin", ROLE_ADMIN));

    //@Transactional
    public Usuario read(String username) {
        for (Usuario details : this.details)
            if (details.getUsername().equalsIgnoreCase(username))
                return details;

        return null;
    }
}
