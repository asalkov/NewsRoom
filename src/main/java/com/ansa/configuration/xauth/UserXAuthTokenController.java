package com.ansa.configuration.xauth;

import com.ansa.usermanagement.User;
import com.ansa.usermanagement.UserService;
import com.ansa.usermanagement.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "/auth")
public class UserXAuthTokenController {
    private final TokenUtils tokenUtils = new TokenUtils();
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    private final UserService userService = new UserServiceImpl();
    @Autowired
    public UserXAuthTokenController(AuthenticationManager am, @Qualifier("customUserDetailsService")UserDetailsService userDetailsService) {
        this.authenticationManager = am;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/register", method = { RequestMethod.POST})
    public AuthResult register(@RequestParam String username, @RequestParam String password) {
        if (this.userDetailsService.loadUserByUsername(username)!=null)
            return AuthResult.DUPLICATE_USER;

        userService.registerUser(username, password);
        return AuthResult.SUCCESS;
    }

    @RequestMapping(value = "/authenticate", method = { RequestMethod.POST })
    public AuthResult authorize(@RequestParam String username, @RequestParam String password) {
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//        Authentication authentication = this.authenticationManager.authenticate(token);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserDetails details = this.userDetailsService.loadUserByUsername(username);
//
//        Map<String, Boolean> roles = new HashMap<String, Boolean>();
//        for (GrantedAuthority authority : details.getAuthorities())
//            roles.put(authority.toString(), Boolean.TRUE);
//
//        return new UserTransfer(details.getUsername(), roles, tokenUtils.createToken(details));


        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.getUserByUserName(username);
            return new AuthResult(tokenUtils.createToken(user), "", Status.OK);
        } catch (AuthenticationException ex){
            return new AuthResult("", ex.getMessage(), Status.ERROR);
        }



        //return new UserInfo(user.getUsername(), tokenUtils.createToken(user));
    }

    public static class UserInfo{
        private final String username;
        private final String token;

        public UserInfo(String username, String token){
            this.username = username;
            this.token = token;
        }

        public String getUsername(){
            return username;
        }

        public String getToken(){
            return token;
        }
    }
    public static class UserTransfer {

        private final String name;
        private final Map<String, Boolean> roles;
        private final String token;

        public UserTransfer(String userName, Map<String, Boolean> roles, String token) {

            Map<String, Boolean> mapOfRoles = new ConcurrentHashMap<String, Boolean>();
            for (String k : roles.keySet())
                mapOfRoles.put(k, roles.get(k));

            this.roles = mapOfRoles;
            this.token = token;
            this.name = userName;
        }

        public String getName() {
            return this.name;
        }

        public Map<String, Boolean> getRoles() {
            return this.roles;
        }

        public String getToken() {
            return this.token;
        }
    }
}
