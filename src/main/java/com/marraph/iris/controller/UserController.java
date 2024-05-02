package com.marraph.iris.controller;

import com.marraph.iris.model.organisation.User;
import com.marraph.iris.service.organisation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/v1/user")
@RestController
public final class UserController extends AbstractController<User> {

    @Autowired
    public UserController(UserService userService) {
        super(userService);
    }
}
