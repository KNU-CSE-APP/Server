package com.knu.cse.image;

import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class S3UploadController {

    private final S3UploadService uploader;

    @ApiOperation(value = "이미지 업로드 테스트", notes = "이미지를 Amazon S3 에 업로드하여 저장된 link를 얻을 수 있다.")
    @PostMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        return uploader.upload(file, "static");
    }
}
