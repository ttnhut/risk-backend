package com.utc2.riskmanagement.services.impl;

import com.utc2.riskmanagement.entities.User;
import com.utc2.riskmanagement.exception.ResourceNotFoundException;
import com.utc2.riskmanagement.repositories.UserRepository;
import com.utc2.riskmanagement.utils.ExceptionConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findById(username).orElseThrow(()
                -> new ResourceNotFoundException(ExceptionConstant.User.RESOURCE,
                ExceptionConstant.User.ID_FIELD, username));
        return user;
    }
}
