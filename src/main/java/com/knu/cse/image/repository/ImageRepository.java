package com.knu.cse.image.repository;

import com.knu.cse.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {

    void deleteByBoard_IdAndUrl(Long boardId,String url);
}
