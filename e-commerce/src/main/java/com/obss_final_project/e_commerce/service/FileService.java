package com.obss_final_project.e_commerce.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    ResponseEntity<String> uploadFile(MultipartFile file);

    ResponseEntity<Resource> downloadFile(String fileName);

    ResponseEntity<String> deleteFile(String fileName);
}
