/*
 * Copyright 2022 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.example.ppmtool.services;

import com.example.ppmtool.domain.User;
import com.example.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Transactional
    public User loadUserById(Long id) {
//        User user = userRepository.getById(id); // Exception: could not initialize proxy
        Optional<User> optional = userRepository.findById(id);
        User user = optional.get();
        if (user == null) {
            new UsernameNotFoundException("User not found");
        }
        return user;
    }
}