package gov.co.eden.service;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface AWSS3Service {
    boolean doesBucketExist(String bucketName);

    Bucket createBucket(String bucketName);

    List<Bucket> listBuckets();

    void deleteBucket(String bucketName);

    PutObjectResult putObject(String bucketName, String key, File file);

    ObjectListing listObjects(String bucketName);

    S3Object getObject(String bucketName, String objectKey);

    CopyObjectResult copyObject(
            String sourceBucketName,
            String sourceKey,
            String destinationBucketName,
            String destinationKey
    );

    void deleteObject(String bucketName, String objectKey);

    DeleteObjectsResult deleteObjects(DeleteObjectsRequest delObjReq);

    String uploadFile(MultipartFile imagen, String complementPath) throws IOException;
}
