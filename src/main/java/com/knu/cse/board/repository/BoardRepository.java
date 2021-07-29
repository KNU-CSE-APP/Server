package com.knu.cse.board.repository;

import com.knu.cse.board.domain.Board;
<<<<<<< HEAD

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {

=======
import org.springframework.data.jpa.repository.JpaRepository;

>>>>>>> efc5890d9c1c70c398d5a0ed138c7b40f8600618
import com.knu.cse.board.domain.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board,Long> {


    @Query("select b from Board b where b.title LIKE %:title%")
    Optional<List<Board>> findByTitle(@Param(value="title") String title);

    Optional<List<Board>> findByAuthor(String author);

    Optional<List<Board>> findByCategory(Category category);

    @Query("select b from Board b where b.content LIKE %:content%")
    Optional<List<Board>> findByContent(@Param(value="content") String content);

<<<<<<< HEAD


=======
>>>>>>> efc5890d9c1c70c398d5a0ed138c7b40f8600618
}
