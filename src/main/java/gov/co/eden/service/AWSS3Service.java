package gov.co.eden.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AWSS3Service {

    void deleteObject(String imageURL);

    String uploadFile(MultipartFile imagen, String complementPath) throws IOException;
}
