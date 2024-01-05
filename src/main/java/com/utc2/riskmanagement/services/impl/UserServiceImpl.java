package com.utc2.riskmanagement.services.impl;

import com.utc2.riskmanagement.entities.MasterData;
import com.utc2.riskmanagement.entities.User;
import com.utc2.riskmanagement.exception.ResourceExistException;
import com.utc2.riskmanagement.exception.ResourceNotFoundException;
import com.utc2.riskmanagement.payloads.UserDTO;
import com.utc2.riskmanagement.repositories.MasterDataRepository;
import com.utc2.riskmanagement.repositories.RoleRepository;
import com.utc2.riskmanagement.repositories.UserRepository;
import com.utc2.riskmanagement.services.EmailService;
import com.utc2.riskmanagement.services.FileService;
import com.utc2.riskmanagement.services.UserService;
import com.utc2.riskmanagement.utils.ActivationConstant;
import com.utc2.riskmanagement.utils.EmailConstant;
import com.utc2.riskmanagement.utils.ExceptionConstant;
import com.utc2.riskmanagement.utils.ModelMapperUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Autowired
    private FileService fileService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MasterDataRepository masterDataRepository;

    @Override
    public UserDTO getSingleUser(String email) {
        User user = this.userRepository.findById(email).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.User.RESOURCE, ExceptionConstant.User.ID_FIELD, email));
        return this.modelMapperUtil.getModelMapper().map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userList = this.userRepository.findAll();
        return userList.stream().map(u -> this.modelMapperUtil.getModelMapper().map(u, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        boolean userExisted = this.userRepository.findById(userDTO.getEmail()).isPresent();
        if (!userExisted) {
            MasterData masterData = this.masterDataRepository.findById(userDTO.getType().getId()).get();
            User user = this.modelMapperUtil.getModelMapper().map(userDTO, User.class);
            user.setImage(fileService.uploadImage(user.getFile()));
            user.setEnabled(true);
            user.setCode(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (masterData.getValue().equals("NORMAL")) {
                user.setRole(this.roleRepository.findByName("ROLE_USER"));
            }
            else {
                user.setRole(this.roleRepository.findByName("ROLE_ADMIN"));
            }
            User savedUser = this.userRepository.save(user);
            try {
                this.emailService.sendActivationEmail(savedUser.getEmail(), ActivationConstant.ACTIVATION_URL + savedUser.getCode(), EmailConstant.ACTIVATION_SUBJECT, ActivationConstant.ACTIVATION_DESCRIPTION, savedUser.getName());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return this.modelMapperUtil.getModelMapper().map(savedUser, UserDTO.class);
        }
        else {
            throw new ResourceExistException(ExceptionConstant.User.RESOURCE, ExceptionConstant.User.ID_FIELD, userDTO.getEmail());
        }
    }

    @Override
    public UserDTO update(String email, UserDTO userDTO) {
        User user = this.userRepository.findById(email).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.User.RESOURCE, ExceptionConstant.User.ID_FIELD, email));
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        if (Objects.nonNull(userDTO.getImage())) {
            user.setImage(fileService.uploadImage(userDTO.getFile()));
        }
        if (Objects.nonNull(user.getCode())) {
            user.setCode(null);
        }
        user.setImage(userDTO.getImage());
        User savedUser = this.userRepository.save(user);
        return this.modelMapperUtil.getModelMapper().map(savedUser, UserDTO.class);
    }

    @Override
    public void delete(String email) {
        User user = this.userRepository.findById(email).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.User.RESOURCE, ExceptionConstant.User.ID_FIELD, email));
        this.userRepository.delete(user);
    }

    @Override
    public void activateUser(String code) {
        User user = this.userRepository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.User.RESOURCE, ExceptionConstant.User.CODE_FIELD, code));
        user.setCode(null);
        user.setEnabled(true);
        this.userRepository.save(user);
    }

}
