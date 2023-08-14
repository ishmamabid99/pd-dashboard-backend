package com.pdcollab.blogs.utils;

import org.springframework.beans.factory.annotation.Value;

public class FileStorageConfig {
    @Value("${upload.base-dir}")
    private String uploadDir;
}
