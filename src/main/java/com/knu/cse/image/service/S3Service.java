package com.knu.cse.image.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    private final AmazonS3 amazonS3;


    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName){
        amazonS3.putObject(new PutObjectRequest(bucket,fileName,inputStream,objectMetadata).withCannedAcl(
            CannedAccessControlList.PublicRead));
    }

    public void deleteFile(String url,String prefix){
        String fileName=url.substring(url.lastIndexOf('/')+1);
        log.info("fileName:",fileName);
        try{
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket,prefix+'/'+fileName);
            amazonS3.deleteObject(deleteObjectRequest);
        }
        catch(AmazonServiceException e){
            throw new IllegalStateException("파일 삭제에 오류가 있습니다");
        }
    }

    public String getFileUrl(String fileName){
        return String.valueOf(amazonS3.getUrl(bucket,fileName));
    }
}
