package com.knu.cse.comment.repository;

import com.knu.cse.comment.domain.Comment;
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
}
