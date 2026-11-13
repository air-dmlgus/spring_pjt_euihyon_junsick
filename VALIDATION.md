# 검증 결과

- JDK 21.0.9에서 Maven package 성공.
- Spring Boot + MockMvc + MyBatis XML + H2 통합 테스트: 2개, 실패 0, 오류 0.
- 등록 및 생성 id/Location, 목록, 상세 및 조회수, 부분 수정, 빈 수정, 삭제, 없는 글 404 확인.
- 잘못된 경로/JSON/빈 본문 400, Content-Type 415, 메서드 405 확인.
- 최종 실행 JAR 서버 시작 및 실제 HTTP 목록 요청 200 확인.
- MySQL 서버 연결 테스트는 실행하지 않음. MySQL 프로필과 Docker Compose 설정 제공.
