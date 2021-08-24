package com.knu.cse.image.service;

import com.knu.cse.board.domain.Board;
import com.knu.cse.image.S3UploadService;
import com.knu.cse.image.domain.Image;
import com.knu.cse.image.repository.ImageRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final ImageRepository imageRepository;
    private final S3UploadService uploadService;

    public void saveImage(Board board,List<MultipartFile> images) throws IOException {
        for(MultipartFile image : images){
            String imageUrl = uploadService.upload(image, "static");
            Image img = new Image(imageUrl,board);
            imageRepository.save(img);
        }
    }

    public void deleteImage(Long boardId,List<String> imageUrl){
        for(String url:imageUrl){
            imageRepository.deleteByBoard_IdAndUrl(boardId,url);
        }
    }

}
