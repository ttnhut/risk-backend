package com.utc2.riskmanagement.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RiskDTO implements Serializable {
    private String id;
    private String name;
    private String description;
    private String image;
    private MasterDataDTO level;
    private MasterDataDTO progress;
    private MasterDataDTO reportedClass;
    private MasterDataDTO device;
    private MasterDataDTO riskType;
    private UserDTO assignee;
    private UserDTO reporter;
    private Date createdDate;
    private Date completedDate;
    private MultipartFile file;

    @JsonIgnore
    public MultipartFile getFile() {
        return file;
    }

    @JsonProperty
    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
