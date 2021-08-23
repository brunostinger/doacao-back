package com.cp5a.doacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class LoginSuccessHandler implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String email = ((UserDetails) event.getAuthentication().getPrincipal()).getUsername();
        userService.updateLastLoginDateFoLoggedUser(email);
    }
}
