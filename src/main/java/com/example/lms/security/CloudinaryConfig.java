package com.example.lms.security;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dgqsct9kq",
                "api_key", "327787944665349",
                "api_secret", "vn6tTra8MFV9YlI0zm2-i5jz6zg",
                "secure", true
        ));
    }
}

