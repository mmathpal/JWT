package com.codeience.demo.jwt.controller;

import com.codeience.demo.jwt.model.JwtRequest;
import com.codeience.demo.jwt.model.JwtResponse;
import com.codeience.demo.jwt.service.CustomUserDetailsService;
import com.codeience.demo.jwt.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @GetMapping("/")
    public String home(){
        return "Welcome to Codeience";
    }

    @GetMapping("/home")
    public String user(){
        return "Welcome to user's home page";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Welcome to admin's home page";
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUserName(),
                            jwtRequest.getPassword()
                    )
            );
        }catch(BadCredentialsException e){
            throw  new Exception("Invalid_Credentials", e);
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtRequest.getUserName());

        final String token = jwtUtility.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
