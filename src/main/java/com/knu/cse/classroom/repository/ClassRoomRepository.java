package com.knu.cse.classroom.repository;

import com.knu.cse.classroom.domain.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {

}