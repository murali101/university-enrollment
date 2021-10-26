package com.university.enrollment.security;

import com.university.enrollment.model.entities.User;
import com.university.enrollment.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * login authenication service
 */

@Slf4j
@Service
public class AuthenticationService implements UserDetailsService {

    private UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * validate based on username
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("USER NOT PRESENT IN THE SYSTEM - " + username);
        } else {
            log.info("USER EXISTS IN THE SYSTEM ==> {}", user);
        }

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), true, true,
                true, true, createAuthorities(user.getRole().name()));
    }

    private Collection<GrantedAuthority> createAuthorities(String role) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return  authorities;
    }

}
