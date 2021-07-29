package com.knu.cse.classroom.service;

import com.knu.cse.classroom.domain.Building;
import com.knu.cse.classroom.domain.ClassRoom;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class ClassRoomServiceTest {

//    @Autowired private ClassRoomService classRoomService;
//
//    @Test
//    @Rollback(value=true)
//    public void 강의실등록_Test(){
//        ClassRoom classRoom = classRoomService.RegistrationClassRoom(Building.IT4, 104L, 20L);
//    }

}