package com.utc2.riskmanagement.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;

@Component
public class CloudinaryUtil {
    public Cloudinary getCloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name","djznt6vp1",
                "api_key","928569841325554",
                "api_secret","aViV-xFN5oFIariik61G9VNRh2M",
                "secure",true
        ));
        return cloudinary;
    }
}
