package gov.co.eden.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import gov.co.eden.service.AWSS3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public boolean doesBucketExist(String bucketName) {
        return s3client.doesBucketExist(bucketName);
    }

    @Override
    public Bucket createBucket(String bucketName) {
        return s3client.createBucket(bucketName);
    }

    @Override
    public List<Bucket> listBuckets() {
        return s3client.listBuckets();
    }

    @Override
    public void deleteBucket(String bucketName) {
        s3client.deleteBucket(bucketName);
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, File file) {
        return s3client.putObject(bucketName, key, file);
    }

    @Override
    public ObjectListing listObjects(String bucketName) {
        return s3client.listObjects(bucketName);
    }

    @Override
    public S3Object getObject(String bucketName, String objectKey) {
        return s3client.getObject(bucketName, objectKey);
    }

    @Override
    public CopyObjectResult copyObject(
            String sourceBucketName,
            String sourceKey,
            String destinationBucketName,
            String destinationKey
    ) {
        return s3client.copyObject(
                sourceBucketName,
                sourceKey,
                destinationBucketName,
                destinationKey
        );
    }

    @Override
    public void deleteObject(String bucketName, String objectKey) {
        s3client.deleteObject(bucketName, objectKey);
    }

    @Override
    public DeleteObjectsResult deleteObjects(DeleteObjectsRequest delObjReq) {
        return s3client.deleteObjects(delObjReq);
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

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipart.getResource().getFilename());
        multipart.transferTo(convFile);
        return convFile;
    }

}
