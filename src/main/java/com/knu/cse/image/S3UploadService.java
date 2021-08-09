package com.knu.cse.image;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3UploadService {

    @Value("${my.tempFilePath}")
    public String TEMP_FILE_PATH;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File convertedFile = convert(multipartFile);
        return upload(convertedFile, dirName);
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            return;
        }
        log.info("임시 파일이 삭제 되지 못했습니다. 파일 이름: {}", targetFile.getName());
    }

    private File convert(MultipartFile file) throws IOException {
        String abSolutePath =  System.getProperty("user.dir");
        File dir = new File(abSolutePath+File.separator+TEMP_FILE_PATH);
        log.info("dirname:",abSolutePath+File.separator+TEMP_FILE_PATH);
        if(!dir.exists()){
            dir.mkdirs();
            log.info("디렉토리생성");
        }
        File convertFile = new File(TEMP_FILE_PATH + file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return convertFile;
        }
        throw new IllegalArgumentException(String.format("파일 변환이 실패했습니다. 파일 이름: %s", file.getName()));
    }

}
