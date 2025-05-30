package com.example.emsuser.controller;

import com.example.emsuser.dto.UserDetailsResponseDTo;
import com.example.emsuser.exception.CustomException;
import com.example.emsuser.model.UserModel;
import com.example.emsuser.security.JwtTokenProvider;
import com.example.emsuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/general")
public class GeneralController {
    @Autowired
    UserService userService;
   @Autowired
   private JwtTokenProvider jwtTokenProvider;
    @GetMapping("/validate")
    public String validate(@CookieValue("jwt_token") String token) {


        UUID userId = jwtTokenProvider.getUserIdFromJWT(token);
        if(userId==null){
            throw new CustomException("Invalid token");
        }

        return userId.toString();

    }
    @GetMapping("/user")
    public ResponseEntity<UserDetailsResponseDTo> getUserDetails(@CookieValue("jwt_token") String token){
        UUID userId = jwtTokenProvider.getUserIdFromJWT(token);
        UserModel user = userService.getUserById(userId);
        UserDetailsResponseDTo response = new UserDetailsResponseDTo();
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().getRole());
        return ResponseEntity.ok(response);
    }

}
