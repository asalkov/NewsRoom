package com.ansa.configuration.xauth;

import com.ansa.dto.Usuario;
import com.ansa.dto.UsuarioDAO;
import com.ansa.usermanagement.USER_ROLE;
import com.ansa.usermanagement.User;
import com.ansa.usermanagement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Service
public class CustomUserDetailsService extends SpringBeanAutowiringSupport implements UserDetailsService {

//    public static final String ROLE_ADMIN = "ADMIN";
//    public static final String ROLE_USER = "USER";

    @Autowired
    UsuarioDAO usuarioDAO;

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Usuario usr = usuarioDAO.read(username);
        User user = userService.findUser(username);
        //UserDetails details = new SimpleUserDetails(usr.getUsername(), usr.getContrasenia(), ROLE_USER, usr.getRol());
        if (user == null)
            throw new UsernameNotFoundException(username);
        UserDetails details = new SimpleUserDetails(user.getUsername(), user.getPassword(), USER_ROLE.GENERAL.name(), "");

        return details;
    }
}
