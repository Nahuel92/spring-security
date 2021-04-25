package org.nahuel.rodriguez.springsecuritysecondhandson.controllers;

import org.nahuel.rodriguez.springsecuritysecondhandson.entities.Otp;
import org.nahuel.rodriguez.springsecuritysecondhandson.entities.User;
import org.nahuel.rodriguez.springsecuritysecondhandson.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {
    private final UserService userService;

    public AuthController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/add")
    public void addUser(@RequestBody final User user) {
        userService.addUser(user);
    }

    @PostMapping("/user/auth")
    public void auth(@RequestBody final User user) {
        userService.auth(user);
    }

    @PostMapping("/otp/check")
    public void check(@RequestBody final Otp otp, final HttpServletResponse response) {
        if (userService.check(otp)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
