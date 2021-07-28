package com.example.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    LoginUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<LoginUser> user = userRepository.findByUserName(userName);

        if(user.isPresent()){
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Arrays.stream(user.get().getRoles().split(",")).forEach(authority ->{
                authorities.add(new SimpleGrantedAuthority(authority));
            });
            return new User(user.get().getUserName(), user.get().getPassword(), authorities);
        }
        else {
            throw new UsernameNotFoundException("User " + userName + " does not exist...");
        }
    }
}
