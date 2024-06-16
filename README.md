# 러닝챌린지 

## history
- [240501 이슈 및 해결방안](history/240501.md)

## 개요
러닝챌린지 애플리케이션은 사용자를 관리하고 챌린지에 참여 및 관리할 수 있는 웹 애플리케이션입니다.

## 주요 기능

### 1. 사용자 관리 (User Management)
- **User Entity**: 사용자 정보를 저장하는 엔터티로, ID, 이메일, 패스워드, 역할, 프로필 메시지와 이미지 등을 포함합니다.
- **DTOs**:
    - `UserDTO`: 사용자 정보를 교환하기 위한 데이터 전송 객체.
    - `UpdateUserRequest`: 사용자의 정보를 업데이트하기 위한 요청을 담는 객체.
- **User Service**:
    - `getUserById`: 사용자의 ID로 사용자를 조회합니다.
    - `getAllUsers`: 모든 사용자를 조회합니다.
    - `updateUser`: 사용자의 정보를 업데이트합니다.
    - `deleteUser`: 사용자를 삭제합니다.
- **User Controller**:
    - `getUser`: 사용자 ID로 사용자를 조회합니다.
    - `getAllUsers`: 모든 사용자를 조회합니다.
    - `updateUser`: 사용자의 정보를 업데이트합니다.
    - `deleteUser`: 사용자를 삭제합니다.
    - `createUser`: 사용자를 등록합니다.

### 2. 챌린지 관리 (Challenge Management)
- **Challenge Entity**: 챌린지 정보를 저장하는 엔터티로, ID, 제목, 설명, 목표, 생성자 등을 포함합니다.
- **DTOs**:
    - `ChallengeDTO`: 챌린지 정보를 교환하기 위한 데이터 전송 객체.
- **Challenge Service**:
    - `getChallengeById`: 챌린지의 ID로 챌린지를 조회합니다.
    - `getAllChallenges`: 모든 챌린지를 조회합니다.
    - `saveChallenge`: 챌린지를 생성합니다.
    - `updateChallenge`: 챌린지 정보를 업데이트합니다.
    - `deleteChallenge`: 챌린지를 삭제합니다.
- **Challenge Controller**:
    - `getChallenge`: 챌린지 ID로 챌린지를 조회합니다.
    - `getAllChallenges`: 모든 챌린지를 조회합니다.
    - `updateChallenge`: 챌린지 정보를 업데이트합니다.
    - `deleteChallenge`: 챌린지를 삭제합니다.
    - `createChallenge`: 챌린지를 생성합니다.

### 3. 챌린지 참여 관리 (Challenge Participation)
- **ChallengeParticipant Entity**: 챌린지 참여 정보를 저장하는 엔터티로, ID, 사용자, 챌린지, 진행 상황, 시작 및 완료 날짜 등을 포함합니다.
- **DTOs**:
    - `ChallengeParticipantDTO`: 챌린지 참여 정보를 교환하기 위한 데이터 전송 객체.
- **ChallengeParticipant Service**:
    - `addParticipant`: 챌린지에 참여자를 추가합니다.
    - `removeParticipant`: 챌린지에서 참여자를 제거합니다.
    - `getParticipants`: 특정 챌린지의 참여자 목록을 조회합니다.
- **ChallengeParticipant Controller**:
    - `participateInChallenge`: 챌린지에 참여합니다.
    - `cancelParticipation`: 챌린지 참여를 취소합니다.
    - `getParticipants`: 특정 챌린지의 참여자 목록을 조회합니다.

### 4. 보안 (Security)
- **JWT 기반 인증 및 인가**
    - `filterChain`: HTTP 요청에 대한 보안 필터 체인을 설정합니다. JWT 필터 및 로그인 필터를 설정하며, 모든 요청에 대한 인증 및 권한을 확인합니다.
    - `RunnerUserDetails`: `UserDetails` 인터페이스를 구현하여 인증 정보를 담습니다.

### 5. 예외 처리 (Exception Handling)
- **Global Exception Handler**:
    - `GlobalExceptionHandler`: `@ControllerAdvice`와 AOP를 사용하여 전역 예외를 처리합니다.

### 6. API 문서화 (API Documentation)
- **Swagger**를 통한 API 문서화 설정 및 접근. 

## API 명세서

### 사용자 관리 API

| HTTP Method | Endpoint          | Request Body              | Response Body     | Description               |
|-------------|-------------------|---------------------------|-------------------|---------------------------|
| GET         | `/users/{id}`     |                           | `UserDTO`         | 특정 사용자의 정보를 조회 |
| GET         | `/users`          |                           | `[UserDTO]`       | 모든 사용자를 조회        |
| POST        | `/users`          | `UserDTO`                 | `String`          | 사용자를 생성             |
| PUT         | `/users/{id}`     | `UpdateUserRequest`       | `UserDTO`         | 사용자 정보를 수정        |
| DELETE      | `/users/{id}`     |                           | `String`          | 사용자를 삭제             |

## 챌린지 관리 API

| HTTP Method | Endpoint          | Request Body              | Response Body     | Description               |
|-------------|-------------------|---------------------------|-------------------|---------------------------|
| GET         | `/challenges/{id}`|                           | `ChallengeDTO`    | 특정 챌린지 정보를 조회  |
| GET         | `/challenges`     |                           | `[ChallengeDTO]`  | 모든 챌린지를 조회        |
| POST        | `/challenges`     | `ChallengeDTO`            | `String`          | 챌린지를 생성             |
| PUT         | `/challenges/{id}`| `ChallengeDTO`            | `ChallengeDTO`    | 챌린지 정보를 수정        |
| DELETE      | `/challenges/{id}`|                           | `String`          | 챌린지를 삭제             |

## 챌린지 참여 관리 API

| HTTP Method | Endpoint                                 | Request Body        | Response Body                   | Description               |
|-------------|------------------------------------------|---------------------|----------------------------------|---------------------------|
| POST        | `/challenges/{id}/participant`           | `userId`            | `String`                        | 특정 챌린지에 참여        |
| DELETE      | `/challenges/{id}/participant`           | `userId`            | `String`                        | 특정 챌린지 참여 취소     |
| GET         | `/challenges/{id}/participant/list`      |                     | `[ChallengeParticipantDTO]`     | 특정 챌린지의 참여자 조회 |

## 보안 관련 API

| HTTP Method | Endpoint          | Request Body              | Response Body     | Description               |
|-------------|-------------------|---------------------------|-------------------|---------------------------|
| POST        | `/login`          | `LoginRequest`            | `JWT Token`       | 사용자 로그인             |
| POST        | `/join`           | `UserDTO`                 | `String`          | 사용자 회원가입           |
