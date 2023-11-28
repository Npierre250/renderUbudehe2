package com.example.demo.Services;

import com.example.demo.Domain.CustomUserDetails;
import com.example.demo.Domain.User;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("User: "+userName+" doesnt exist"));

        return user.map(CustomUserDetails::new).get();
    }


}