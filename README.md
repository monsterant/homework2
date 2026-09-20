## 1. 실행 방법

### 환경
- Java 17
- Spring Boot 3.5.16
- Gradle
- H2 Database

### 실행 명령

Windows:

```bash
gradlew.bat bootRun
```

### 실행 후 서버 주소 

http://localhost:8080


## API 명세

1. 생성 POST   '/todo' 
```
주소  :  /todo

요청본문 :
{
    "title": "자바 공부하기"
}

응답본문 : 201 Created
오류응답 : 
```
2. 조회 단건 GET    '/todo/{id}'
```
주소  :  GET /todo/{id}

응답본문 : {
                  "id": 1,
                  "title": "자바 공부하기",
                  "completed": false,
                  "createdAt": "2026-09-19T14:00:00",
                  "updatedAt": "2026-09-19T14:00:00"
            }
오류응답 : 존재하지 않는 id : 404 Not Found
{
  "status": 404,
  "message": "Todo 번호 999 를 찾을 수 없습니다."
}

```
수정 단건 PATCH  '/todo/{id}'
```
주소  :  PATCH /todo/{id}

요청본문 : {
  "title": "Spring 공부하기"
}

응답본문 : {
"id": 1,
"title": "자바 공부하기",
"completed": false,
"createdAt": "2026-09-19T14:00:00",
"updatedAt": "2026-09-19T14:00:00"
}

상태 코드 : 200 OK

오류응답 : 제목 길이 초과 
{
  "status": 400,
  "message": "제목은 100자 이하로 입력해주세요."
}
```

삭제 단건 DELETE '/todo/{id}'

```
주소  :  DELETE '/todo/{id}'

상태 코드 : 204 No Content

오류응답 : 존재하지 않는 id : 404 Not Found
{
  "status": 404,
  "message": "Todo 번호 999 를 찾을 수 없습니다."
}
```

## 설계 설명

```
기본 /todo를 기준으로 진행.

생성 - post
조회 - get
수정 - patch
삭제 - delete

로 구분하였습니다.

- 생성 성공은 새 데이터가 만들어졌기 때문에 201 Created
- 조회와 수정 성공은 정상 처리되었다는 의미로 200 OK
- 삭제 성공은 반환할 본문이 없기 때문에 204 No Content
- 잘못된 제목이나 길이 초과는 잘못된 요청이므로 400 Bad Request
- 존재하지 않는 Todo를 요청한 경우 404 Not Found

DB는 H2로 사용했습니다.
별도의 DB를 설치하지 않고 바로 사용할 수 있으며 학습 과정에서 
H2 사용법이 나왔기에 해당 DB를 사용했습니다.
```
## 4. 실행 결과

IntelliJ HTTP Client(`test.http`)를 사용해서 
Todo 생성 → 목록 조회 → 완료 처리 → 삭제 순서로 확인했습니다.

### 1. Todo 생성
```
요청

POST http://localhost:8080/todo
Content-Type: application/json

{
"title": "자바 공부하기"
}

응답

HTTP/1.1 201 Created
```

### 2. Todo 목록 조회
```
요청

GET http://localhost:8080/todo

응답

HTTP/1.1 200 OK

{
    "id": 1,
    "title": "자바 공부하기",
    "completed": false,
    "createdAt": "2026-09-19T12:57:39.849939",
    "updatedAt": "2026-09-19T12:57:39.849939"
}

```

### 3. Todo 완료 처리
```
요청

PATCH http://localhost:8080/todo/1
Content-Type: application/json

{
"completed": true
}

응답

HTTP/1.1 200 OK

{
    "id": 1,
    "title": "자바 공부하기",
    "completed": true,
    "createdAt": "2026-09-19T12:57:39.849939",
    "updatedAt": "2026-09-19T14:43:15"
}
```

### 4. Todo 삭제
```
요청

DELETE http://localhost:8080/todo/1

응답

HTTP/1.1 204 No Content

응답 본문 없음
```

### 5. 400 Bad Request 확인
```
제목을 공백으로 요청했습니다.

요청

POST http://localhost:8080/todo
Content-Type: application/json

{
    "title": "   "
}

응답

HTTP/1.1 400 Bad Request

{
    "status": 400,
    "message": "제목은 비어 있거나 공백일 수 없습니다."
}
```

### 6. 404 Not Found 확인
```
존재하지 않는 Todo를 조회했습니다.

요청

GET http://localhost:8080/todo/999

응답

HTTP/1.1 404 Not Found

{
    "status": 404,
    "message": "Todo 번호 999 를 찾을 수 없습니다."
}
```