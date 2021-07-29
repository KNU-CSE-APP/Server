# 기능정의

## Product Backlog

1. 회원 가입 & 로그인
    - admin, user
    - 학교 이메일 인증
   

2. 강의실 예약
    - 시간제(계속 사용할려면 연장, 6시간)
    - 강의실 별 자리 지정(중도 예약 처럼)
    - 예약 기록
    - 노쇼 관련
        - QR 코드로 파악
        - gps로 파악
        - 학교 승인이 된다면, 학교 api로 파악
   

3. 자유 게시판
   - 카테고리 별 게시물
   - 게시물에 댓글 및 답글 기능
   

4. 공지사항
    - 야식마차
        - 학생증 확인 등으로 인하여 수작업 필요
    - 학생회 공지사항
    - 관리자 계정으로 글 쓰기
    - 키워드 관련해서 알람 받기 기능
   
   
---

## 기술 스펙

**FE**

- 안드로이드
- iOS
- 공통적으로 Firebase(push) 사용

**BE**

- Spring Boot
- JPA  
- MySQL

**DevOps**

- 도커
- 젠킨스 CI/CD
- Nginx
- AWS (EC2, RDS, S3)

**협업**

- Github - 버전관리
- Notion - 문서, 기능 관리
- Swagger API - API 명세 관리
- Agile scrum - 매주 스프린트 단위로 진행

---

## 회의 날짜

fix : 매주 수요일 19시 30분

유동적으로 바꾸기

---

## ER Diagram

![knucseapp (1)](https://user-images.githubusercontent.com/51476083/127291522-064db17b-6ef8-444c-b5cd-417d5d2b8d58.png)

<br/> <br/>

---

## Server Architecture

![image](https://user-images.githubusercontent.com/51476083/127125991-5b9007ae-2470-45ec-a04b-a5d9cefbca67.png)
