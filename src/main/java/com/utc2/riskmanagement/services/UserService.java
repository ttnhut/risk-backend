package com.utc2.riskmanagement.services;

import com.utc2.riskmanagement.payloads.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getSingleUser(String email);
    List<UserDTO> getAllUsers();
    UserDTO create(UserDTO userDTO);
    UserDTO update(String email, UserDTO userDTO);
    void delete(String email);
    void activateUser(String code);
}
