package com.utc2.riskmanagement.controllers;

import com.utc2.riskmanagement.configs.JWTTokenHelper;
import com.utc2.riskmanagement.payloads.APIResponse;
import com.utc2.riskmanagement.payloads.JWTAuthRequest;
import com.utc2.riskmanagement.payloads.JWTAuthResponse;
import com.utc2.riskmanagement.payloads.UserDTO;
import com.utc2.riskmanagement.services.UserService;
import com.utc2.riskmanagement.utils.APIConStant;
import com.utc2.riskmanagement.utils.ModelMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(APIConStant.Auth.ACTIVATION_ENDPOINT)
    public APIResponse activateUser(@RequestParam(value = "code") String code) {
        userService.activateUser(code);
        return new APIResponse(APIConStant.Auth.ACTIVATION_SUCCESS_MESSAGE, Boolean.TRUE);
    }

    @PostMapping(APIConStant.Auth.LOGIN_ENDPOINT)
    public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest jwtAuthRequest) throws Exception {
        this.authenticate(jwtAuthRequest.getEmail(), jwtAuthRequest.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());
        String token = this.jwtTokenHelper.generateToken(userDetails);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setToken(token);
        jwtAuthResponse.setUserDTO(modelMapperUtil.getModelMapper().map(userDetails, UserDTO.class));

        return ResponseEntity.ok(jwtAuthResponse);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException ex) {
            log.error("Invalid Details");
            throw new Exception("Invalid user name or password");
        }
    }

}
