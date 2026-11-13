# 게시판 REST API
첨부 학습자료의 Controller → Service → DAO → MyBatis XML → DB 구조를 새 프로젝트로 구현했습니다.

## 가장 빠른 실행
JDK 21 설치 후 압축을 풀고 `run.cmd`를 더블클릭하세요. 포함된 실행용 JAR로 서버가 시작됩니다(Maven 다운로드 불필요). 브라우저에서 http://localhost:8080/api/board 를 열면 JSON 목록을 확인할 수 있습니다. 별도 게시판 화면은 없습니다. 종료는 실행 창에서 Ctrl+C.

## 소스에서 실행 (Windows PowerShell)
JDK 21을 설치하고 JAVA_HOME을 JDK 경로로 설정합니다. 프로젝트 폴더에서:
```powershell
.\mvnw.cmd spring-boot:run
```
`start-demo.cmd`를 더블클릭해도 실행됩니다.

최초 실행에는 인터넷이 필요합니다(Maven 및 의존성 다운로드). 기본 demo 모드는 H2 메모리 DB를 사용하므로 MySQL 설치 없이 실행됩니다. 재시작하면 데이터가 초기화됩니다.

- 목록: http://localhost:8080/api/board
- API 요청 예제: `requests.http` (VS Code REST Client 또는 IntelliJ HTTP Client)
- STS: File → Import → Maven → Existing Maven Projects → 이 폴더 선택 → BoardApplication 실행. 프로젝트 JRE 21 확인.
- macOS/Linux: `chmod +x mvnw` 후 `./mvnw spring-boot:run`

## MySQL 사용
Docker 사용 시 `docker compose up -d` 후 MySQL이 준비되면:
```powershell
$env:SPRING_PROFILES_ACTIVE="mysql"
$env:DB_USERNAME="ssafy"
$env:DB_PASSWORD="ssafy"
.\mvnw.cmd spring-boot:run
```
기존 MySQL이라면 먼저 `CREATE DATABASE ssafy_board CHARACTER SET utf8mb4;`를 실행하고 DB 계정을 준비합니다. DB_URL, DB_USERNAME, DB_PASSWORD 환경변수로 접속 정보를 지정합니다. 테이블은 자동 생성하며 기존 데이터를 삭제하지 않습니다. 예시 계정은 로컬 실습용입니다.

## API
| 메서드 | 경로 | 성공 응답 |
|---|---|---|
| GET | /api/board | 200 + JSON 배열 |
| GET | /api/board/{id} | 200 + 객체 (조회수 증가) |
| POST | /api/board | 201 + 요청 객체/생성 id + Location |
| PUT | /api/board/{id} | 200, 빈 본문 |
| DELETE | /api/board/{id} | 204, 빈 본문 |
없는 글의 조회·수정·삭제는 404입니다. 경로 숫자 변환 실패·깨진 JSON은 400, 잘못된 Content-Type은 415입니다.

## PDF와 일치하는 동작
- JSP와 화면 없이 JSON API를 제공합니다. 등록·수정·삭제는 주소창 대신 HTTP 요청 도구를 사용합니다.
- PUT은 title/content 중 null이 아닌 필드만 수정하며 writer는 변경하지 않습니다. 경로 id가 본문 id보다 우선합니다.
- POST는 생성 id를 요청 객체에 채웁니다. regDate는 DB 기본값이므로 등록 응답에는 null이며 상세 재조회에서 확인합니다.
- PUT/DELETE의 존재 확인은 조회수를 증가시키지 않습니다.
- 필수값 검증은 자료처럼 추가하지 않았습니다. 로그인·권한 검증도 없는 학습용 프로젝트입니다.

## 원본 대비 보완·차이
- Java 25 대신 JDK 21, Spring Boot 3.5.7, MyBatis Starter 3.0.5를 사용합니다.
- 기본 H2 demo 프로필과 별도 MySQL 프로필을 제공합니다. SQL은 같은 XML을 공유합니다.
- 변경 행 수 확인, 상세 조회 트랜잭션을 추가했습니다. 사전 조회만으로 동시 삭제를 보장하지 않습니다.
- 빈 수정 JSON도 SQL 문법 오류 없이 성공합니다. MySQL은 기본 useAffectedRows=false(일치 행 수) 연결을 전제로 합니다.
- Swagger 문서 의존성은 제외하고 requests.http를 제공합니다. 삭제 API는 숨기지 않습니다.

## 구조
`controller/BoardRestController` → `service/BoardServiceImpl` → `dao/BoardDao` ↔ `resources/mappers/boardMapper.xml`
`dto/Board`는 JSON과 DB 결과를 담습니다. Spring 메시지 변환기가 JSON을, MyBatis가 DB 매핑을 처리합니다.

## 테스트·빌드
```powershell
.\mvnw.cmd test
.\mvnw.cmd package
java -jar target/board-rest-api-1.0.0.jar
```
테스트는 demo H2에서 실제 Spring MVC·MyBatis·DB를 함께 사용합니다. MySQL 연결은 사용자의 실행 환경에서 별도로 확인해야 합니다.

버전 호환성 참고: https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/
