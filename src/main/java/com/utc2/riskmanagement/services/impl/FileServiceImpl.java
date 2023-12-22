package com.utc2.riskmanagement.services.impl;

import com.cloudinary.utils.ObjectUtils;
import com.utc2.riskmanagement.services.FileService;
import com.utc2.riskmanagement.utils.CloudinaryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private CloudinaryUtil cloudinaryUtil;

    @Override
    public String uploadImage(MultipartFile file) {
        Map r = null;
        try {
            r = this.cloudinaryUtil.getCloudinary().uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String imageURL = (String) r.get("secure_url");
        return imageURL;
    }
}
