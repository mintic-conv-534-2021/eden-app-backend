package gov.co.eden.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.PutObjectResult;
import gov.co.eden.service.AWSS3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Slf4j
@Service
public class AWSS3ServiceImpl implements AWSS3Service {

    private final AmazonS3 s3client;

    @Value("${eden.s3.bucket-name}")
    private String bucketName;

    @Value("${eden.s3.initial-path}")
    private String initialPath;

    @Value("${eden.s3.accessKey}")
    private String accessKey;

    @Value("${eden.s3.secretKey}")
    private String secretKey;

    public AWSS3ServiceImpl() {
        this(new AmazonS3Client() {
        });
    }

    public AWSS3ServiceImpl(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    @Override
    public void deleteObject(String imageURL) {

        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey
        );
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();
        AmazonS3URI as3uri = new AmazonS3URI(imageURL);
        s3client.deleteObject(as3uri.getBucket(), as3uri.getKey());
    }

    @Override
    public String uploadFile(MultipartFile imagen, String complementPath) throws IOException {

        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey
        );
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();

        AWSS3ServiceImpl awsService = new AWSS3ServiceImpl(s3client);

        awsService.putObject(
                bucketName,
                initialPath + complementPath + "/" + imagen.getResource().getFilename(),
                multipartToFile(imagen)
        );
        URL s3Url = s3client.getUrl(bucketName, initialPath + complementPath + "/" + imagen.getResource().getFilename());
        return s3Url.toExternalForm();
    }

    public PutObjectResult putObject(String bucketName, String key, File file) {
        return s3client.putObject(bucketName, key, file);
    }

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipart.getResource().getFilename());
        multipart.transferTo(convFile);
        return convFile;
    }

}
