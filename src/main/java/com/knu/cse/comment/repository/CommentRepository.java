package com.knu.cse.comment.repository;

import com.knu.cse.comment.domain.Comment;
import com.knu.cse.comment.dto.CommentDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    void deleteCommentsByIdOrParentId(Long id,Long parentId);

    Optional<List<Comment>> findByBoard_Id(Long boardId);

    @Query("select m.commentList from Member m where m.id = :memId")
    Optional<List<Comment>> findMyComments(@Param(value="memId") Long memId);

    @Query("select new com.knu.cse.comment.dto.CommentDto(c) from Comment c where c.parentId = :parentId")
    Optional<List<CommentDto>> findRepliesByParent_Id(@Param("parentId") Long parentId);
}
