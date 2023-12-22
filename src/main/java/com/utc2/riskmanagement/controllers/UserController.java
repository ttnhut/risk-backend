package com.utc2.riskmanagement.controllers;

import com.utc2.riskmanagement.payloads.APIResponse;
import com.utc2.riskmanagement.payloads.UserDTO;
import com.utc2.riskmanagement.services.UserService;
import com.utc2.riskmanagement.utils.APIConStant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(APIConStant.User.GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT)
    public UserDTO getSingleUser(@PathVariable(value = "email") String email) {
        return this.userService.getSingleUser(email);
    }

    @GetMapping(APIConStant.User.ENDPOINT)
    public List<UserDTO> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @PostMapping(APIConStant.User.ENDPOINT)
    public UserDTO create(@ModelAttribute UserDTO userDTO) {
        return this.userService.create(userDTO);
    }

    @PutMapping(APIConStant.User.GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT)
    public UserDTO update(@PathVariable(value = "email") String email, @RequestBody UserDTO userDTO) {
        return this.userService.update(email, userDTO);
    }

    @DeleteMapping(APIConStant.User.GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT)
    public APIResponse delete(@PathVariable(value = "email") String email) {
        this.userService.delete(email);
        return new APIResponse(APIConStant.User.getDeleteSuccessStatus(email), Boolean.TRUE);
    }
}
