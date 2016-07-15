package org.academy.java.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
public class AuthController {

    @RequestMapping("/user")
    ResponseEntity<Map<String, Object>> user(Principal user) {

        if (user == null) return new ResponseEntity<>(UNAUTHORIZED);

        return new ResponseEntity<>(Collections.<String, Object>singletonMap("name", user.getName()), OK);
    }

    @RequestMapping("/hello")
    String user() {
        return "{\"message\":\"have a good day\"}";
    }
}
