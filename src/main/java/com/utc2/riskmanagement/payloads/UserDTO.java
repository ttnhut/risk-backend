package com.utc2.riskmanagement.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO implements Serializable {
    private String email;
    private String name;
    private String image;
    private String password;
    private MultipartFile file;
    private MasterDataDTO type;
    private RoleDTO role;
    private Boolean connected = false;


    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }


    @JsonIgnore
    public MultipartFile getFile() {
        return file;
    }

    @JsonProperty
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public UserDTO(String email) {
        this.email = email;
    }
}
