package com.ansa.configuration.xauth;

import com.ansa.dto.Usuario;
import com.ansa.dto.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Service
public class CustomUserDetailsService extends SpringBeanAutowiringSupport implements UserDetailsService {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    @Autowired
    UsuarioDAO usuarioDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usr = usuarioDAO.read(username);
        UserDetails details = new SimpleUserDetails(usr.getUsername(), usr.getContrasenia(), ROLE_USER, usr.getRol());
        return details;
    }
}
