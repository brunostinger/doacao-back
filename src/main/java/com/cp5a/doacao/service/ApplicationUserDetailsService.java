package com.cp5a.doacao.service;

import com.cp5a.doacao.exception.FieldValidationException;
import com.cp5a.doacao.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static java.util.Collections.emptyList;
import com.cp5a.doacao.model.User;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User applicationUser = userRepository.findByEmail(email);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(email);
        }

        if (applicationUser.getUserStatus() == null ||  applicationUser.getUserStatus().getId()!=1) {
            throw new UsernameNotFoundException(email);
        }

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new CustomUserDetail(
                applicationUser.getEmail(),
                applicationUser.getPassword(),
                getAuthority(applicationUser),
                applicationUser.getId());
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        switch(user.getUserType().getId()){
            case 0:
                authorities.add(new SimpleGrantedAuthority("ROLE_ADM"));
                break;
            case 1:
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;
            case 2:
                authorities.add(new SimpleGrantedAuthority("ROLE_ORG"));
                break;
            default:
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }
}